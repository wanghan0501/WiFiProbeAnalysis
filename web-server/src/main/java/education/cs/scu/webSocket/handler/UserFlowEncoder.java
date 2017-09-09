package education.cs.scu.webSocket.handler;

import education.cs.scu.entity.UserVisitBean;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.StringWriter;

/**
 * Created by maicius on 2017/6/18.
 */
public class UserFlowEncoder implements Encoder.Text<UserVisitBean> {
    public String encode(UserVisitBean userDiagramData) throws EncodeException {
        StringWriter writer = new StringWriter();
        //Makes use of the JSON Streaming API to build the JSON string.
        Json.createGenerator(writer)
                .writeStartObject()
                .write("time", String.valueOf(userDiagramData.getTime()))
                .write("totalFlow", userDiagramData.getTotalFlow())
                .write("checkInFlow", userDiagramData.getCheckInFlow())
                .write("checkInRatio", userDiagramData.getCheckInRate())
                .write("deepVisitRatio", userDiagramData.getDeepVisitRate())
                .write("jumpRatio", userDiagramData.getShallowVisitRate())
                .writeEnd()
                .flush();
        //System.out.println(writer.toString());
        return writer.toString();
    }

    public void init(EndpointConfig endpointConfig) {

    }

    public void destroy() {

    }
}
