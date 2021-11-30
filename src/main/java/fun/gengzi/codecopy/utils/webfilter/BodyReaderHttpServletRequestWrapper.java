package fun.gengzi.codecopy.utils.webfilter;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;

/**
 * 当从请求中获取流以后，流被filter中的这个 inputStreamToString(InputStream in) 这个方法处理后就被“消耗”了，
 * 这会导致，chain.doFilter(request, res)这个链在传递 request对象的时候，里面的请求流为空，导致责任链模式下，
 * 其他下游的链无法获取请求的body,从而导致程序无法正常运行，这也使得我们的这个filter虽然可以获取请求信息，但是它会导致整个应用程序不可用，那么它也就失去了意义；
 *
 *
 *针对第二种方式的缺陷：流一旦被读取，就无法向下传递整个问题，有如下解决方案；
 *解决思路如下：将取出来的字符串，再次转换成流，然后把它放入到新request 对象中，在chain.doFiler方法中 传递新的request对象；要实现这种思路，需要自定义一个类
 *继承HttpServletRequestWrapper，然后重写public BufferedReader getReader()方法，
 * public  ServletInputStream getInputStream()方法；（这两个方法的重写实现逻辑如下：getInputStream（）方法中将body体中的字符串转换为字节流（它实质上返回的是一个ServletInputStream 对象）；
 * 然后通过getReader()调用---->getInputStream（）方法；），继承实现重写逻辑以后，在自定义分filter（VersionCheckFilter）中，
 * 使用自定义的HttpServletRequestWrapper（BodyReaderHttpServletRequestWrapper）将原始的HttpServletRequest对象进行再次封装；
 * 再通过BodyReaderHttpServletRequestWrapper对象去做dofilter(req,res)的req对象；
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        System.out.println("-------------------------------------------------");
        Enumeration e = request.getHeaderNames()   ;
        while(e.hasMoreElements()){
            String name = (String) e.nextElement();
            String value = request.getHeader(name);
            System.out.println(name+" = "+value);

        }
        body = HttpHelper.getBodyString(request).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    @Override
    public String getHeader(String name) {
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return super.getHeaderNames();
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return super.getHeaders(name);
    }

}
