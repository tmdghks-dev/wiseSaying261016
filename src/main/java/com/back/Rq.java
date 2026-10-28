package com.back;

// import 2개: HashMap, Map
import java.util.HashMap;
import java.util.Map;

public class Rq {
    // 필드 1: String actionName                               → "삭제?id=1" 에서 "삭제" 담을 곳
    private String actionName;
    // 필드 2: Map<String, String> paramsMap = new HashMap<>() → {"id"="1"} 담을 서랍장
    private Map<String,String> paramsMap = new HashMap<>();
    // ===== 생성자: 여기서 cmd를 한 번만 쪼개서 위 필드 2개를 채운다 =====
    public Rq(String cmd) {
        // 1) cmd를 "\\?" 기준으로, 최대 2조각으로 쪼개기 → String[] cmdBits
        //    "삭제?id=1" → ["삭제", "id=1"]      "목록" → ["목록"]
        String[] cmdBits = cmd.split("\\?", 2);

        // 2) actionName = cmdBits[0]
        actionName = cmdBits[0].trim();

        // 3) 조각이 1개뿐이면 (= ?가 없음 = 파라미터 없음) → return;  (paramsMap은 빈 채로 끝)
        if (cmdBits.length == 1) return;

        // 4) String queryString = cmdBits[1]
        //    "page=5&searchKeyword=영광"
        String queryString = cmdBits[1].trim();

        // 5) queryString을 "&" 기준으로 쪼개기 → String[] params
        //    ["page=5", "searchKeyword=영광"]
        String[] params = queryString.split("&");

        // 6) for (String param : params) 로 하나씩 돌면서
        for (String param : params) {
            //    6-1) param을 "=" 기준으로, 최대 2조각으로 쪼개기 → String[] bits
            //         "page=5" → ["page", "5"]     "id=" → ["id", ""]     "id" → ["id"]
            String[] bits = param.split("=", 2);

            //    6-2) 조각이 2개가 아니면 → continue;   ("id" 처럼 = 자체가 없는 경우 건너뜀)
            if (bits.length != 2) continue;

            //    6-3) String key = bits[0].trim();  String value = bits[1].trim();
            String key = bits[0].trim();
            String value = bits[1].trim();

            //    6-4) value가 비어 있으면(isEmpty) → continue;   ("id=" 같은 경우 건너뜀)
            if (value.isEmpty()) continue;

            //    6-5) paramsMap.put(key, value);   서랍장에 넣기
            paramsMap.put(key, value);
        }
    }

    // ===== "삭제" 꺼내기 =====
    public String getActionName() {
        // actionName return
        return actionName;
    }

    // ===== 글자로 꺼내기 =====
    public String getParam(String paramName, String defaultValue) {
        // paramsMap.getOrDefault(paramName, defaultValue) return
        // → 서랍에 있으면 그 값, 없으면 기본값
        return paramsMap.getOrDefault(paramName, defaultValue);
    }

    // ===== 숫자로 꺼내기 =====
    public int getParamAsInt(String paramName, int defaultValue) {
        // 1) String value = getParam(paramName, "");   위 메서드 재활용
        String value = getParam(paramName, "");

        // 2) value가 비어 있으면(isEmpty) → defaultValue return   (파라미터 없음)
        if (value.isEmpty()) return defaultValue;

        // 3) try {
        //        Integer.parseInt(value) return          ("1" → 1)
        //    } catch (NumberFormatException e) {
        //        defaultValue return                     ("일번"처럼 숫자가 아니면)
        //    }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}