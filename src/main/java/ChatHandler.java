import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sk
 * @time 2022/5/19
 **/
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            //第一次请求
            logger.info("第一次请求 = 准备提取 token");
            //转化为http请求
            FullHttpRequest request = (FullHttpRequest) msg;
            //拿到请求地址
            String uri = request.uri();
            //判断是不是websocket请求，如果是拿出我们传递的参数（我的是token）
            String connection = request.headers().get("Connection");
            String upgrade = request.headers().get("Upgrade");
            String authorization = request.headers().get("Sec-WebSocket-Protocol");
            request.headers().set("Sec-WebSocket-Protocol", authorization);
            //request.headers().remove("Sec-WebSocket-Protocol");
            System.err.println(authorization);
            if (!"Upgrade".equals(connection) || !"websocket".equals(upgrade)) {
                ctx.close();
                ctx.channel().writeAndFlush("error");
                return;
            } else {
//                if (null != uri && uri.contains("/websocket") && uri.contains("?")) {
//                    String[] uriArray = uri.split("\\?");
//                    if (uriArray.length > 1) {
//                        String[] params = uriArray[1].split("&");
//                        Map<String, String> map = new HashMap<>();
//                        for (String param : params) {
//                            String[] temp = param.split("=");
//                            if (temp.length > 1) {
//                                map.put(temp[0], temp[1]);
//                            }
//                        }
//                        if (map.containsKey("token")) {
//                            String token = map.get("token");
//                            System.err.println("获取token" + token);
//                        }
//                        if (map.containsKey("roomId")) {
//                            String roomId = map.get("roomId");
//                            ChannelSupervise.add(roomId, ctx.channel());
//                        }
//                    }
//                    //重新设置请求地址
//                    //request.setUri("/websocket");
//                }
            }
        }
        //接着建立请求
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

    }
}
