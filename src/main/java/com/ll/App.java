package com.ll;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    int lastTextId = 0;
    List<Text> texts = new ArrayList<>();
    String dataFilePath; // 파일 경로를 저장할 변수

    App() {
        scanner = new Scanner(System.in);
        lastTextId = 0;
        texts = new ArrayList<>();
        dataFilePath = "texts.txt"; // 기본 파일 경로를 설정합니다.
        loadFromFile(); // 파일에서 데이터를 불러옵니다.
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
                    saveToFile(); // 파일에 데이터를 저장합니다.
                    return;
                case "등록":
                    write();
                    break;
                case "목록":
                    list();
                    break;
                case "삭제":
                    remove(rq);
                    break;
                case "수정":
                    modify(rq);
                    break;
                default:
                    System.out.println("잘못된 명령입니다. 다시 입력하시오.\n");
            }
        }
    }

    void write() {
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

    void list() {
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

    void remove(Rq rq) {
        int id = rq.getParamAsInt("id", 0);

        if (id == 0) {
            System.out.println("id를 정확히 입력해주세요.");
            return;
        }

        int index = getIndexOfTextById(id);

        if (index == -1) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        texts.remove(index);

        System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
    }

    int getIndexOfTextById(int id) {
        for (int i = 0; i < texts.size(); i++) {
            Text text = texts.get(i);

            if (text.id == id) {
                return i;
            }
        }

        return -1;
    }

    void modify(Rq rq) {
        int id = rq.getParamAsInt("id", 0);

        if (id == 0) {
            System.out.println("id를 정확히 입력해주세요.");
            return;
        }

        System.out.printf("%d번 명언을 수정합니다.\n", id);

        int index = getIndexOfTextById(id);

        if (index == -1) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        Text text = texts.get(index);

        System.out.printf("명언(기존) : %s\n", text.content);
        System.out.print("명언 : ");
        String content = scanner.nextLine();

        System.out.printf("작가(기존) : %s\n", text.authorName);
        System.out.print("작가 : ");
        String authorName = scanner.nextLine();

        text.content = content;
        text.authorName = authorName;

        System.out.printf("%d번 명언을 수정되었습니다.\n", id);
    }

    // 데이터를 파일에 저장
    void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath))) {
            for (Text text : texts) {
                writer.write(text.id + "|" + text.authorName + "|" + text.content);
                writer.newLine();
            }
            System.out.println("데이터를 파일에 저장했습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일에서 데이터를 불러옴
    void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            texts.clear(); // 기존 데이터를 모두 지웁니다.
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String authorName = parts[1];
                    String content = parts[2];
                    Text text = new Text(id, content, authorName);
                    texts.add(text);
                }
            }
            System.out.println("파일에서 데이터를 불러왔습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
