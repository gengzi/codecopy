package fun.gengzi.codecopy.business.problemsolve.thread;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Example {

    public static void main(String[] args) {
        InputStream inputStream = null;
        try {
            inputStream = new URL("http://localhost:8080").openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 这里使用hutool工具类，将流写入文件  可以忽略
        // FileWriter fileWriter = FileWriter.create(new File("D:\\ideaworkspace\\1.jpg"));
        // File file = fileWriter.writeFromStream(inputStream);
    }

}
