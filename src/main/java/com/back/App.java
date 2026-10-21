package com.back;

import java.util.Scanner;

public class App {
    Scanner sc = new Scanner(System.in);
    private int lastid=0;
    private WiseSaying[] wiseSayings =new WiseSaying[100];

    public void run() {
        System.out.println("== 명언 앱 ==");

        while(true){
            System.out.print("명령) ");
            String cmd = sc.nextLine().trim();

            if(cmd.equals("종료")){
                break;
            }else if(cmd.equals("등록")){
                actionWrite();
            }else if(cmd.equals("목록")){
                actionList();
            }else if(cmd.startsWith("수정?id=")){
                actionModify(cmd);
            }else if(cmd.startsWith("삭제?id=")){
                actionDelete(cmd);
            }
        }
    }

    private void actionDelete(String cmd) {
       int id = Integer.parseInt(cmd.substring(6));
       boolean deleted = delete(id);
       if(!deleted){
           System.out.println(id + " 번 명언은 존재하지 않습니다.");
           return;
        }
            System.out.println(id +"번 명언이 삭제되었습니다..");
    }
    private boolean delete(int id) {
        WiseSaying wiseSaying = findById(id);
        if (wiseSaying == null) return false;
        wiseSayings[wiseSaying.getId() - 1] = null;
        return true;
    }

    private void actionModify(String cmd) {
        int id = Integer.parseInt(cmd.substring(6));
        WiseSaying wiseSaying = findById(id);

        if (wiseSaying == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }

        System.out.println("명언(기존) : " + wiseSaying.getContent());
        System.out.print("명언 : ");
        String content = sc.nextLine().trim();
        System.out.println("작가(기존) : " + wiseSaying.getAuthor());
        System.out.print("작가 : ");
        String author = sc.nextLine().trim();

        modify(wiseSaying, content, author);
    }

    private void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);
    }

    private void actionList() {
        //등록된 목록 보여줌
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for(int i =lastid-1; i>=0;i--){
            WiseSaying wiseSaying = wiseSayings[i];
            if(wiseSaying==null){
                continue;
            }
            System.out.println(wiseSayings[i].getId() + " / " + wiseSayings[i].getAuthor()+ " / "+ wiseSayings[i].getContent() );
        }

    }

    private WiseSaying write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(++lastid, content, author);
        wiseSayings[lastid - 1] = wiseSaying;
        return wiseSaying;
    }

    private void actionWrite() {
        //등록을 한다면 명언 작가를 물어본다
        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();

        WiseSaying wiseSaying = write(content, author);
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");


    }
    
    private WiseSaying findById(int id){
        for(int i = 0; i< lastid; i++){
            WiseSaying wiseSaying = wiseSayings[i];
            if(wiseSaying != null && wiseSaying.getId() == id){
                return wiseSaying;
            }
        }
        return null;
    }
}