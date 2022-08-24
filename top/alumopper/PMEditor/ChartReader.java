package top.alumopper.PMEditor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.media.CannotRealizeException;
import javax.media.NoPlayerException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class ChartReader {

    public ArrayList<Line> lines = new ArrayList<Line>();
    public Song song;           //曲子
    public double songTime;     //曲子时间
    public JSONObject chart;    //谱面数据
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
        chart = JSON.parseObject(String.valueOf(builder));
        //读取note
        for (JSONObject line : chart.getJSONArray("lines").toJavaList(JSONObject.class)) {
            Line l = new Line(line.getFloat("speed"));
            for (JSONObject note : line.getJSONArray("notes").toJavaList(JSONObject.class)) {
                if(note.getInteger("type") == Note.TAP){
                    l.notes.add(new Tap(
                                    note.getInteger("key"),
                                    note.getDouble("time")
                            )
                    );
                }else{
                    l.notes.add(new Drag(
                                    note.getInteger("key"),
                                    note.getDouble("time")
                            )
                    );
                }
            }
            lines.add(l);
        }
        //
        try {
            song = new Song("./res/charts/" + Editor.chart + "/" + chart.getJSONObject("song").getString("name"),chart.getJSONObject("song").getFloat("bpm"));
        } catch (NoPlayerException e) {
            e.printStackTrace();
        } catch (CannotRealizeException e) {
            e.printStackTrace();
        }

        songTime = (int)(song.songPlayer.getDuration().getSeconds()*100)/100.0;
    }

    public boolean addNote(Note n, int lineNo){
        for (Note qwq: lines.get(lineNo).notes ) {
            if(qwq.equals(n)){
                return false;
            }
        }
        ((JSONObject)chart.getJSONArray("lines").get(lineNo)).getJSONArray("notes").add(JSON.toJSON(n));
        lines.get(lineNo).notes.add(n);
        return true;
    }

    public void delNote(Note n, int lineNo){
        //JSON去除
        Iterator<Object> o = ((JSONObject)chart.getJSONArray("lines").get(lineNo)).getJSONArray("notes").iterator();
        while (o.hasNext()){
            JSONObject curNote = (JSONObject) o.next();
            if(curNote.getDoubleValue("time") == n.time && curNote.getIntValue("key") == n.key){
                o.remove();
            }
        }
        //数列去除
        Iterator<Note> qwq = lines.get(lineNo).notes.iterator();
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
            f.write(chart.toString(SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
