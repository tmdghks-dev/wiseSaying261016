package com.back;

// import 3개: ArrayList, List, Scanner

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    // 필드 1: Scanner sc
    Scanner sc = new Scanner(System.in);
    // 필드 2: int lastid = 0  (번호표 기계)
    int lastid = 0;
    // 필드 3: List<WiseSaying> wiseSayings = new ArrayList<>()  (레일)
    List<WiseSaying> wiseSayings = new ArrayList<>(); // ✅ 이름 wisesaying → wiseSayings (여러 개라 s, 꺼낸 한 개 wiseSaying과 구분)
    public void run() {
        // "== 명언 앱 ==" 출력
        System.out.println("== 명언 앱 ==");
        // while(true) 시작
        while(true) {
            // "명령) " 출력 (print, 줄바꿈 X)
            System.out.print("명령) ");
            // cmd = 입력받고 trim
            String cmd = sc.nextLine().trim();
            // cmd를 Rq로 해석 ("삭제?id=1" → 이름 "삭제" / 서랍장 {id=1})
            Rq rq = new Rq(cmd);

            // 모던 switch로 명령 분기
            switch (rq.getActionName()) {
                case "종료" -> {
                    return; // ✅ break는 switch만 빠져나감 → return으로 run() 자체를 끝냄
                }
                case "등록" -> actionWrite();
                case "목록" -> actionList();
                case "삭제" -> actionDelete(rq);
                case "수정" -> actionModify(rq);
            }
            // while 끝

        }
    }

    // ===== 등록 =====
    private void actionWrite() {
        // "명언 : " 출력, content 입력받기
        System.out.print("명언 : "); // ✅ 콜론 뒤 한 칸
        String content = sc.nextLine().trim(); // ✅ 받은 글자를 content 변수에 담음
        // "작가 : " 출력, author 입력받기
        System.out.print("작가 : "); // ✅ 콜론 뒤 한 칸
        String author = sc.nextLine().trim(); // ✅ 받은 글자를 author 변수에 담음
        // write(content, author) 호출해서 결과를 WiseSaying 변수에 받기
        WiseSaying wiseSaying = write(content, author);
        // "?번 명언이 등록되었습니다." 출력 (번호는 받은 변수.getId())
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    private WiseSaying write(String content, String author) {
        // new WiseSaying(++lastid, content, author) 만들기
        WiseSaying wiseSaying = new WiseSaying(++lastid, content, author);
        // 리스트에 add
        wiseSayings.add(wiseSaying); // ✅ 레일(리스트)에 접시 올리기
        // 만든 거 return
        return wiseSaying;
    }

    // ===== 목록 =====
    private void actionList() {
        // "번호 / 작가 / 명언" 출력
        System.out.println("번호 / 작가 / 명언");
        // "----------------------" 출력
        System.out.println("----------------------");
        // for: i = 리스트.size() - 1 부터, i >= 0 까지, i--
        for(int i = wiseSayings.size() -1; i>=0; i--){
            // 리스트.get(i)로 꺼내서 WiseSaying 변수에 담기
            WiseSaying wiseSaying = wiseSayings.get(i);
            // 꺼낸변수.getId() + " / " + getAuthor() + " / " + getContent() 출력
            System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent()); // ✅ " / " 띄어쓰기
        }


    }

    // ===== 찾기 =====
    private WiseSaying findById(int id) {
        return wiseSayings
                .stream()
                .filter(wiseSaying ->wiseSaying.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ===== 삭제 =====
    private void actionDelete(Rq rq) {
        // id = rq에서 숫자로 꺼내기 (없거나 숫자가 아니면 -1)
        int id = rq.getParamAsInt("id", -1);

        if (id == -1) {
            System.out.println("id를 숫자로 입력해주세요.");
            return;
        }
        // delete(id) 결과를 boolean deleted에 받기
        boolean deleted = delete(id);
        // deleted가 false면 → "?번 명언은 존재하지 않습니다." 출력하고 return;
        if(!deleted){ // ✅ 느낌표: 삭제 "못" 했으면
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return; // ✅ 여기서 끝냄 → 아래 줄 실행 안 됨 (else 필요 없음)
        }
        // "?번 명언이 삭제되었습니다." 출력
        System.out.println(id + "번 명언이 삭제되었습니다."); // ✅ 붙여 쓰기
    }

    private boolean delete(int id) {
        return wiseSayings.removeIf(wiseSaying -> wiseSaying.getId() == id);
    }

    // ===== 수정 =====
    private void actionModify(Rq rq) {
        // id = rq에서 숫자로 꺼내기 (없거나 숫자가 아니면 -1)
        int id = rq.getParamAsInt("id", -1);

        if (id == -1) {
            System.out.println("id를 숫자로 입력해주세요.");
            return;
        }
        // findById(id)로 찾기
        WiseSaying wiseSaying = findById(id); // ✅ 찾은 걸 변수에 담음
        // null이면 → "?번 명언은 존재하지 않습니다." 출력하고 return;
        if(wiseSaying == null){ // ✅ "찾은 게 null이면" 이라고 비교해야 함
            System.out.println(id+"번 명언은 존재하지 않습니다.");
            return; // ✅ 없으면 여기서 끝
        }
        // "명언(기존) : " + 찾은거.getContent() 출력
        System.out.println("명언(기존) : " + wiseSaying.getContent()); // ✅ 리스트가 아니라 찾은 한 개한테 물어봄
        // "명언 : " 출력, content 입력받기
        System.out.print("명언 : "); // ✅
        String content = sc.nextLine().trim(); // ✅
        // "작가(기존) : " + 찾은거.getAuthor() 출력
        System.out.println("작가(기존) : " + wiseSaying.getAuthor()); // ✅
        // "작가 : " 출력, author 입력받기
        System.out.print("작가 : "); // ✅
        String author = sc.nextLine().trim(); // ✅

        // modify(찾은거, content, author) 호출
        modify(wiseSaying, content, author); // ✅
    }

    private void modify(WiseSaying wiseSaying, String content, String author) {
        // setContent(content)
        wiseSaying.setContent(content); // ✅
        // setAuthor(author)
        wiseSaying.setAuthor(author); // ✅
    }
}