package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    String response; //서버 응답

    Handler handler = new Handler(); // 토스트를 띄우기 위한 메인스레드 핸들러 객체 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button socketConnectBtn = findViewById(R.id.socketConnectBtn);

        /*
        버튼을 클릭했을 때
        1. 입력상자의 서버 IP 주소와 전송할 데이터 가져오기
        2. 소켓통신을 위한 스레드의 매개변수로 넣어주어 스레드 객체 생성
        3. 스레드 시작
        */
//        socketConnectBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SocketThread thread = new SocketThread();
//                thread.start();
//            }
//        });
    }

    class SocketThread extends Thread{

        String host; // 서버 IP
        String data; // 전송 데이터
        private BufferedReader br;
        private PrintWriter pw;
        public SocketThread(){
            this.host = "192.168.0.6";
        }

        @Override
        public void run() {

            try{

                int port = 5000; //포트 번호는 서버측과 똑같이
                Socket socket = new Socket(host, port); // 소켓 열어주기
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pw = new PrintWriter(socket.getOutputStream());
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("content", "hihi");

                    pw.println(new Gson().toJson(jsonObject)); // 출력 스트림에 데이터 넣기
                    pw.flush(); // 출력
                }catch (JSONException e) {
                    Log.e("yongju", e.getMessage());
                    Log.e("yongju", e.toString());
                }
                try {
                    socket.close(); // 소켓 해제
                }catch(IOException e){
                    Log.e("yongju",e.toString());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}