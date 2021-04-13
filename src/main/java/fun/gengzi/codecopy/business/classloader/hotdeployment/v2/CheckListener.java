package fun.gengzi.codecopy.business.classloader.hotdeployment.v2;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RuntimeUtil;
import fun.gengzi.codecopy.business.classloader.hotdeployment.HotDeploymentClassLoaderV2;
import fun.gengzi.codecopy.business.luckdraw.entity.SysUser;
import lombok.SneakyThrows;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class CheckListener {


    public static void listener() {
        // 持续监听
        // 检索代码变化
        File file = FileUtil.file("D:\\ideaworkspace\\codecopy\\codecopy\\target\\classes\\fun\\gengzi\\codecopy\\business\\classloader\\hotdeployment\\v2\\");
        //这里只监听文件或目录的修改事件
        WatchMonitor watchMonitor = WatchMonitor.create(file, WatchMonitor.ENTRY_MODIFY);
        watchMonitor.setWatcher(new Watcher() {
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("创建：{}-> {}", currentPath, obj);

            }

            @SneakyThrows
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("修改：{}-> {}", currentPath, obj);
                // 重新创建classloader 加载对应类
                SysApplication.restart();


            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("删除：{}-> {}", currentPath, obj);
            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("Overflow：{}-> {}", currentPath, obj);
            }
        });
        //设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
        watchMonitor.setMaxDepth(3);
        //启动监听
        watchMonitor.start();
    }


}
