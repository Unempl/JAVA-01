import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * This is a custom ClassLoader
 */
public class HelloXClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Class<?> clazz = new HelloXClassLoader().findClass("Hello");
            clazz.getMethod("hello").invoke(clazz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] realData = new byte[0];
        try {
            realData = file2Byte();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, realData, 0, realData.length);
    }

    //Get the bytecode of Class Hello
    private byte[] file2Byte() throws IOException {
        InputStream ins = getClass().getClassLoader().getResourceAsStream("Hello.xlass");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int text;
        while ((text = ins.read()) != -1) {
            bos.write(text);
        }
        return getCorrectByte(bos.toByteArray());
    }

    //decode
    private byte[] getCorrectByte(byte[] realDate) {
        for (int i = 0; i < realDate.length; i++) {
            realDate[i] = (byte) (255 - realDate[i]);
        }
        return realDate;
    }
}
