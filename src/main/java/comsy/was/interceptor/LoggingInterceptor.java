package comsy.was.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import comsy.was.component.RequestInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;
    private final RequestInfo requestInfo;

    private StopWatch stopWatch;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        stopWatch = new StopWatch();
        stopWatch.start();

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

        stopWatch.stop();
        Long total = stopWatch.getTotalTimeMillis();

        // GSON
        Gson gson = new Gson();
        JsonObject logging2 = new JsonObject();
        JsonObject reqObject = gson.fromJson(objectMapper.readTree(cachingRequest.getContentAsByteArray()).toString(), JsonObject.class);
        JsonObject resObject = gson.fromJson(objectMapper.readTree(cachingResponse.getContentAsByteArray()).toString(), JsonObject.class);
        logging2.add("req", reqObject);
        logging2.add("res", resObject);
        logging2.addProperty("time", total);
        logging2.addProperty("apiExecutionTime", requestInfo.getApiExecutionTime());
        log.info(logging2.toString());

        // JSON simple - ????????? ?????? put ??? ???????????? ????????? ????????? ????????? ?????? ??????.
//        JSONObject logging = new JSONObject();
//        logging.put("req", objectMapper.readTree(cachingRequest.getContentAsByteArray()));
//        logging.put("res", objectMapper.readTree(cachingResponse.getContentAsByteArray()));
//        logging.put("time", total);
//        logging.put("apiExecutionTime", requestInfo.getApiExecutionTime());
//        log.info(logging.toJSONString());

//        log.info(
//                "{\"req\":{},\"res\":{},\"time\":{},\"apiExecutionTime\":{}}",
//                objectMapper.readTree(cachingRequest.getContentAsByteArray()),
//                objectMapper.readTree(cachingResponse.getContentAsByteArray()),
//                total,                              // request ?????? ?????? interceptor ????????? ????????????
//                requestInfo.getApiExecutionTime()   // Controller.api ????????????
//        );

    }
}