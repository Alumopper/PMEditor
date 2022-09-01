package top.alumopper.PMEditor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class ChartReader {
    public Song song;           //曲子
    public Chart chart;    //谱面数据
    public File chartFile;      //谱面文件

    public ChartReader(String s) throws IOException {
        //读取谱面
        String content = "";
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
                }
            }
        } catch (NoPlayerException e) {
            e.printStackTrace();
        } catch (CannotRealizeException e) {
            e.printStackTrace();
        }

        this.chart.song.time = (float)((song.songPlayer.getDuration().getSeconds()*100)/100.0);
    }

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
        Iterator<Note> qwq = chart.lines.get(lineNo).notes.iterator();
        while (qwq.hasNext()){
            Note curNote = qwq.next();
            if(curNote.equals(n)){
                qwq.remove();
            }
        }
    }

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
