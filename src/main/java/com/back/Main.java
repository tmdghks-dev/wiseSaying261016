package com.back;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        Scanner sc = new Scanner(System.in);

        WiseSaying[] wiseSayings = new WiseSaying[100];
        int count = 0;

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

                count++;
                wiseSayings[count - 1] = new WiseSaying(count, content, author);
                System.out.println(count + "번 명언이 등록되었습니다.");
            } else if (cmd.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");

                for (int i = count - 1; i >= 0; i--) {
                    WiseSaying ws = wiseSayings[i];
                    if(ws == null)continue;
                    System.out.println(ws.id + " / " + ws.author + " / " + ws.content);
                }
            } else if (cmd.startsWith("삭제?id=")) {
                int id =Integer.parseInt(cmd.substring(6));
                boolean found = false;
                for(int i = 0; i < count; i++) {
                    if(wiseSayings[i] != null && wiseSayings[i].id == id){
                        wiseSayings[i] =null;
                        found = true;
                    }
                }
                if(found){
                    System.out.println(id + "번 명언이 삭제 되었습니다.");
                }else{
                    System.out.println(id +"번 명언은 존재하지 않습니다.");
                }

            }
        }
        sc.close();
    }
}