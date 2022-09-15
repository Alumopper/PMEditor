package top.alumopper.PMEditor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import top.alumopper.PMEditor.Component.ClickOp;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * 读取谱面的Json文件
 */
public class ChartReader {
    /**
     * 音频
     */
    public Song song;
    /**
     * 谱面数据
     */
    public Chart chart;
    /**
     * 谱面文件
     */
    public final File chartFile;      //谱面文件

    /**
     * 创建一个读谱器
     * @param s 谱面文件的路径
     * @throws IOException 如果出现io异常
     */
    public ChartReader(String s) throws IOException {
        //读取谱面
        String content;
        StringBuilder builder = new StringBuilder();

        File file = new File(s);
        chartFile = file;
        InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(streamReader);

        while ((content = bufferedReader.readLine()) != null)
            builder.append(content);

        //JSON解析
        JSONObject chart = JSON.parseObject(String.valueOf(builder));
        this.chart = JSON.toJavaObject(chart,Chart.class);
        try {
            song = new Song("./res/charts/" + Editor.chart + "/" + chart.getJSONObject("song").getString("name"),chart.getJSONObject("song").getFloat("bpm"));
            for (Line l : this.chart.lines) {
                for (Note n : l.notes) {
                    n.effect = Manager.createRealizedPlayer(new File("res/media/tap.wav").toURI().toURL());
                    //检查note是否合法
                    if(n.isIllegalTap()){
                        Editor.currFrame.addInfo("存在非法note","位于"+String.format("%.2f",n.time)+"，第"+n.key+"列",1, new ClickOp());
                    }
                }
            }
        } catch (NoPlayerException | CannotRealizeException e) {
            e.printStackTrace();
        }

        this.chart.song.time = (float)((song.songPlayer.getDuration().getSeconds()*100)/100.0);
    }

    /**
     * 向指定判定线添加一个note。note不可重叠
     * @param n 一个note
     * @param lineNo 判定线编号
     * @return 若note成功添加，返回true
     */
    public boolean addNote(Note n, int lineNo){
        for (Note qwq: chart.lines.get(lineNo).notes ) {
            if(qwq.equals(n)){
                return false;
            }
        }
        //((JSONObject)chart.getJSONArray("lines").get(lineNo)).getJSONArray("notes").add(JSON.toJSON(n));
        chart.lines.get(lineNo).addNote(n);
        return true;
    }

    /**
     * 从指定的判定线删除指定的note。
     * @param n 要被删除的note。判定线中与之相同的note将被删除
     * @param lineNo 判定线编号
     */
    public void delNote(Note n, int lineNo){
        //JSON去除
//        Iterator<Object> o = ((JSONObject)chart.getJSONArray("lines").get(lineNo)).getJSONArray("notes").iterator();
//        while (o.hasNext()){
//            JSONObject curNote = (JSONObject) o.next();
//            if(curNote.getDoubleValue("time") == n.time && curNote.getIntValue("key") == n.key){
//                o.remove();
//            }
//        }
        //数列去除
        chart.lines.get(lineNo).notes.removeIf(curNote -> curNote.equals(n));
    }

    /**
     * 保存谱面文件
     */
    public void save(){
        //保存
        FileWriter f;
        try {
            f = new FileWriter(chartFile);
            f.write("");
            f.flush();
            f.write(JSON.toJSONString(chart,SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
