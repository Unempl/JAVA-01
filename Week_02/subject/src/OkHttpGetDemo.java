import okhttp3.*;

public class OkHttpGetDemo {
    public static void main(String[] args) {
        try {
            //创建okhttpclient对象
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder().url("http://localhost:8088/api/hello").get().build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                //打印出响应的内容，这里显示hello world
                System.out.println(responseBody.string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}