package unitTests.mocks;

import com.github.GuilhermeBauer.Ecommerce.data.vo.v1.security.TokenVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockToken {


    public TokenVO mockVVO() throws ParseException {
        TokenVO tokenVO = new TokenVO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        tokenVO.setUserName("John");
        tokenVO.setAuthenticated(true);
        tokenVO.setCreated(dateFormat.parse("2024-03-21T22:41:29.502+00:00"));
        tokenVO.setExpiration(dateFormat.parse("2024-03-21T23:41:29.502+00:00"));
        tokenVO.setAccessToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJzdWIiOiJzaWx2YSIsInJvbGVzIjpbIlVTRVIiXSwiaXNzIjoiaHR0cDovL2" +
                "xvY2FsaG9zdDo4MDgwIiwiZXhwIjoxNzExMDY0NDg5LCJpYXQiOjE3MTEwNjA4ODl9.SSxKcMqp1pnbxdG" +
                "gH3fd-3yIXHXWEaBl5iqiHLbMrgU");
        tokenVO.setRefreshToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaWx2YSIsInJv" +
                "bGVzIjpbIlVTRVIiXSwiZXhwIjoxNzExMDcxNjg5LCJpYXQiOjE3MTEwNjA4ODl9.JsT1iuPyD0R" +
                "vY-vKToQaO2dVZMMTUZlZqI-ydadEPW4");

        return tokenVO;
    }

    public List<TokenVO> mockVVOList() throws ParseException {
        List<TokenVO> tokenVOList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            tokenVOList.add(mockVVO());
        }
        return tokenVOList;
    }
}
