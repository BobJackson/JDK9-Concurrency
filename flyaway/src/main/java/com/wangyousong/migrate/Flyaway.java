package com.wangyousong.migrate;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 解决的问题：为了让在IDEA中运行源码，同时目录结构发生了一些变化，主要的是没有分core,task,data等目录，直接把所有的java文件放在一起(这不是一个好的工程实践，只是看代码的时候方便)
 * 同时在两个IDEA窗口中不断copy文件比较繁琐（不断地点开原来的目录结构，一层一层手动点开很烦，所以干脆自己写个代码实现自动化copy）
 * <p>
 * <p>
 * 没有解决的问题：（分两类问题，都很好解决）
 * 一、源码错误
 * 1. import com.wangyousong.concurrency.ch7.recipe09.Account;
 * 应该是import com.wangyousong.concurrency.ch7.recipe10.Account;这是源码本身错误（Account就是在recipe10包中）。
 * <p>
 * 2.import com.wangyousong.concurrency.ch8.reciper0.Task; 因该是
 * import com.wangyousong.concurrency.ch8.recipe07.Task;这是源码本身错误（单词拼写错误reciper应该是recipe）
 * <p>
 * <p>
 * 3.import com.wangyousong.concurrency.ch7.recipe09.Decrementer;
 * import com.wangyousong.concurrency.ch7.recipe09.Incrementer;
 * <p>
 * 应该是 import com.wangyousong.concurrency.ch7.recipe10.Decrementer;
 * import com.wangyousong.concurrency.ch7.recipe10.Incrementer;这是源码本身错误（Decrementer，Incrementer就是在recipe10包中）。
 * <p>
 * 二、项目结构问题
 * 1.导入jar问题，以前用的是lib，不是现在主流的maven项目，这个需要手动处理一下。
 */
public class Flyaway {

    public static void main(String[] args) throws IOException {
//        makeFirstClassDir();
//        makeSubDirs();
        copeSrcJavaFiles();
    }

    private static void copeSrcJavaFiles() throws IOException {
        // 获取旧目录中的src目录(包括其子目录)下所有java文件
        for (int i = 4; i <= 11; i++) {
            File firstClassDir = new File("D:\\coding\\Java-9-Concurrency-Cookbook-Second-Edition\\Chapter" + processPrefix(i));
            File[] files = firstClassDir.listFiles();
            for (File f : Objects.requireNonNull(files)) {
                List<File> javaFiles = new ArrayList<>();
                directoryProcess(f, javaFiles);
                // 拷贝文件到 新的目录下
                for (File javaFile : javaFiles) {
                    // 替换所有的包名,去掉所有Main类中以com.packtpub.java9开头的语句
                    File copy = new File(FileUtils.getTempDirectoryPath() + javaFile.getName());
                    FileUtils.copyFile(javaFile, copy);
                    makeACopeSource(copy, "com.wangyousong.concurrency." + "ch" + i + "." + "recipe" + getLastTwoFileName(f) + ";", i, f);
                }
            }
        }
    }

    private static void makeACopeSource(File copy, String packageName, int index, File f) {
        try {
            List<String> lines = FileUtils.readLines(copy, UTF_8);
            List<String> linesCopy = new CopyOnWriteArrayList<>(lines);
            // 第一行所有的包名都使用新的
            linesCopy.set(0, "package " + packageName);

            // 可以优化：可以不用全部遍历完：一般都是类申请之前，下面的替换也只是替换import语句
            for (int i = 0; i < linesCopy.size(); i++) {
                String original = linesCopy.get(i);
                if (original.startsWith("import com.packtpub.")) {
                    replaceImportStatements(linesCopy, i, original);
                }
            }

            StringBuilder sb = new StringBuilder(linesCopy.size());
            for (String s : linesCopy) {
                sb.append(s).append("\n");
            }
            File randomDir = new File(FileUtils.getTempDirectoryPath() + File.separator + UUID.randomUUID().toString());
            if (!randomDir.exists()) {
                randomDir.mkdirs();
            }
            File finalFile = new File(randomDir + File.separator + copy.getName());
            FileUtils.write(finalFile, sb.toString(), UTF_8);

            // print -- time-consuming task
//            printToVerify(finalFile);

            // copy
            FileUtils.copyFileToDirectory(finalFile, new File("D:\\test\\idea2020\\src\\com\\wangyousong\\concurrency\\" + "ch" + index + "\\" + "recipe" + getLastTwoFileName(f)));

            // clean
            finalFile.deleteOnExit();
            randomDir.deleteOnExit();
            copy.deleteOnExit();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void replaceImportStatements(List<String> linesCopy, int i, String original) {
        String firstRep = original.replace("import com.packtpub.java9.concurrency.cookbook.", "import com.wangyousong.concurrency.")
                .replace("import com.packtpub.java8.concurrency.cookbook.", "import com.wangyousong.concurrency.");
        if (firstRep.contains("chapter")) {
            String head = firstRep.substring(0, firstRep.indexOf("chapter"));
            String midOne = "ch" + processStringNumber(original.substring(47, 57).substring(7, 9)) + ".";
            String midTwo = original.substring(57, 65);
            String tail = firstRep.substring(firstRep.lastIndexOf("."));
            linesCopy.set(i, head + midOne + midTwo + tail);
        } else if (firstRep.contains("appendix")) {
            String head = firstRep.substring(0, firstRep.indexOf("appendix"));
            String midOne = "ch11.";
            String midTwo = original.substring(56, 64);
            String tail = firstRep.substring(firstRep.lastIndexOf("."));
            linesCopy.set(i, head + midOne + midTwo + tail);
        }
    }

    public static String processStringNumber(String strNum) {
        try {
            int num = Integer.parseInt(strNum);
            return String.valueOf(num);
        } catch (NumberFormatException e) {
            String substring = strNum.substring(1);
            return String.valueOf(Integer.valueOf(substring));
        }
    }

    private static void printToVerify(File finalFile) throws IOException {
        System.out.println(finalFile.getAbsolutePath());
        String content = FileUtils.readFileToString(finalFile, UTF_8);
        System.out.println(content);
        System.out.println("----------------------------------");
    }

    private static String getLastTwoFileName(File f) {
        String filename = f.getName();
        return filename.substring(filename.length() - 2);
    }

    private static void makeSubDirs() {
        // 读取子目录，并生成同样的子目录结构
        for (int i = 4; i <= 11; i++) {
            File firstClassDir = new File("D:\\coding\\Java-9-Concurrency-Cookbook-Second-Edition\\Chapter" + processPrefix(i));
            File[] files = firstClassDir.listFiles();
            assert files != null;
            int subDirLength = files.length;
            for (int j = 1; j <= subDirLength; j++) {
                File f = new File("D:\\test\\idea2020\\src\\com\\wangyousong\\concurrency\\" + "ch" + i + "\\" + "recipe" + processPrefix(j));
                if (!f.exists()) {
                    f.mkdirs();
                }
            }
        }
    }

    private static String processPrefix(int i) {
        return i < 10 ? "0" + i : String.valueOf(i);
    }

    private static void makeFirstClassDir() {
        // 先生成一级目录
        for (int i = 5; i <= 11; i++) {
            File file = new File("D:\\test\\idea2020\\src\\com\\wangyousong\\concurrency\\" + "ch" + i);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    private static void directoryProcess(File file, List<File> javaFiles) {
        File[] list = file.listFiles();
        if (list != null) {
            for (File f : list) {
                if (f.isDirectory()) {
                    // If is a directory, process it
                    directoryProcess(f, javaFiles);
                } else {
                    // If is a file, process it
                    fileProcess(f, javaFiles);
                }
            }
        }
    }

    private static void fileProcess(File file, List<File> javaFiles) {
        if (file.getName().endsWith(".java")) {
            javaFiles.add(file);
        }
    }
}
