package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    int lastTextId = 0;
    List<Text> texts = new ArrayList<>();
    App() {
        scanner = new Scanner(System.in);
        lastTextId = 0;
        texts = new ArrayList<>();
    }
    void run() {
        System.out.println("====== 명언 앱 ======");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();

            if (cmd.equals("종료")) {
                break;
            }
            else if (cmd.equals("등록")) {
                write();
            }
            else if (cmd.equals("목록")) {
                list();
            }
            else if (cmd.startsWith("삭제?")) {
                Remove(cmd);
            }
            else{
                System.out.println("잘못된 명령입니다. 다시 입력하시오.\n");
            }

        }
    }
    void write(){
        System.out.print("명언 : ");
        String content = scanner.nextLine();

        System.out.print("작가 : ");
        String authorName = scanner.nextLine();

        lastTextId++;
        int id = lastTextId;
        Text text = new Text(id, content, authorName);
        texts.add(text);

        System.out.printf("명언 : %s, 작가 : %s\n", content, authorName);
        System.out.printf("%d번 명언이 등록되었습니다.\n\n", lastTextId);
    }
    void list(){
        System.out.println("총 개수 : " + texts.size());


        System.out.println("번호 | 작가 | 명언");
        System.out.println("----------------------");

        if (texts.isEmpty())
            System.out.println("등록된 명언이 없습니다.\n");

        for (int i = texts.size() - 1; i >= 0; i--) {
            Text text = texts.get(i);
            System.out.printf("%d / %s / %s\n", text.id, text.authorName, text.content);
        }
        System.out.println("\n");
    }
    void Remove(String cmd) {
        String[] cmdBits = cmd.split("\\?", 2);
        String action = cmdBits[0];
        String queryString = cmdBits[1];

        String[] queryStringBits = queryString.split("&");

        int id = 0;

        for (int i = 0; i < queryStringBits.length; i++) {
            String queryParamStr = queryStringBits[i];

            String[] queryParamStrBits = queryParamStr.split("=", 2);

            String paramName = queryParamStrBits[0];
            String paramValue = queryParamStrBits[1];

            if (paramName.equals("id")) {
                id = Integer.parseInt(paramValue);
            }
        }

        System.out.printf("%d번 명언을 삭제합니다.\n", id);
    }

}