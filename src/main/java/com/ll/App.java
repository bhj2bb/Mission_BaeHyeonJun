package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    void run() {
        System.out.println("====== 명언 앱 ======");

        int lastTextId = 0;
        List<Text> texts = new ArrayList<>();


        while (true) {
            System.out.print("명령) ");

            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.nextLine();

            if (cmd.equals("종료")) {
                break;
            }

            else if (cmd.equals("등록")) {
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
            else if (cmd.equals("목록")) {
                System.out.println("총 개수 : " + texts.size());


                System.out.println("번호 | 작가 | 명언");
                System.out.println("----------------------");

                if (texts.isEmpty())
                    System.out.println("등록된 명언이 없습니다.\n");

                for (int i = texts.size() - 1; i >= 0; i--) {
                    Text text = texts.get(i);
                    System.out.printf("%d / %s / %s\n\n", text.id, text.authorName, text.content);
                }
            }

            else{
                System.out.println("잘못된 명령입니다. 다시 입력하시오.\n");
            }

        }
    }
}