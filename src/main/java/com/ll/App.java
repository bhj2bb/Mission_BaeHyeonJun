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

            Rq rq = new Rq(cmd);

            System.out.println("rq.getAction : " + rq.getAction());
            System.out.println("rq.getParamAsInt : " + rq.getParamAsInt("id", 0));

            switch (rq.getAction()) {
                case "종료":
                    return;
                case "등록":
                    write();
                    break;
                case "목록":
                    list();
                    break;
                case "삭제":
                    Remove(rq);
                    break;
                case "수정":
                    Modify(rq);
                    break;
                default:
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
    void Remove(Rq rq) {
        int id = rq.getParamAsInt("id", 0);

        if (id == 0) {
            System.out.println("id를 정확히 입력해주세요.");
            return; // 함수를 끝낸다.
        }

        int index = getIndexOfQuotationById(id);

        if (index == -1) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        texts.remove(index);

        System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
    }

    int getIndexOfQuotationById(int id) {
        for (int i = 0; i < texts.size(); i++) {
            Text text = texts.get(i);

            if (text.id == id) {
                return i;
            }
        }

        return -1;
    }
    void Modify(Rq rq) {
        int id = rq.getParamAsInt("id", 0);

        if (id == 0) {
            System.out.println("id를 정확히 입력해주세요.");
            return; // 함수를 끝낸다.
        }

        System.out.printf("%d번 명언을 수정합니다.\n", id);
    }

}