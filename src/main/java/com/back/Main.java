package com.back;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("== 명언 앱 ==");
        Scanner sc = new Scanner(System.in);
        int count = 1;

        int[] arr = new int[100];
        String[] contents = new String[100];
        String[] authors = new String[100];

        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {
                System.out.print("명언 : ");
                String content = sc.nextLine().trim();
                System.out.print("작가 : ");
                String author = sc.nextLine().trim();

                arr[count - 1] = count;
                contents[count - 1] = content;
                authors[count - 1] = author;

                System.out.println(count + "번 명언이 등록되었습니다.");
                count++;

            } else if (cmd.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");

                for (int i = count - 2; i >= 0; i--) {
                    if (arr[i] == 0) continue;
                    System.out.println(arr[i] + " / " + authors[i] + " / " + contents[i]);
                }

            } else if (cmd.startsWith("삭제?id=")) {
                int id = Integer.parseInt(cmd.substring(6));

                for (int i = 0; i < count - 1; i++) {
                    if (arr[i] == id) {
                        arr[i] = 0;
                        System.out.println(id + "번 명언이 삭제되었습니다.");
                    }
                }
            }
        }
        sc.close();
    }
}