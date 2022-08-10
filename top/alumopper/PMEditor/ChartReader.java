package top.alumopper.PMEditor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.media.CannotRealizeException;
import javax.media.NoPlayerException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ChartReader {

    ArrayList<Line> lines = new ArrayList<Line>();
    public Song song;
    public double songTime;
    public JSONObject chart;    //谱面文件

    public ChartReader(String s) throws IOException {
        //读取谱面
        String content = "";
        StringBuilder builder = new StringBuilder();

        File file = new File(s);
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
                l.notes.add(new Note(
                        note.getInteger("key"),
                        note.getDouble("time"),
                        note.getInteger("type")
                        )
                );
            }
            lines.add(l);
        }
        //
        try {
            song = new Song("res/"+chart.getJSONObject("song").getString("name"),chart.getJSONObject("song").getFloat("bpm"));
        } catch (NoPlayerException e) {
            e.printStackTrace();
        } catch (CannotRealizeException e) {
            e.printStackTrace();
        }

        songTime = (int)(song.songPlayer.getDuration().getSeconds()*100)/100.0;
    }

    public void addNote(Note n, int lineNo){
        ((JSONObject)chart.getJSONArray("lines").get(lineNo)).getJSONArray("notes").add(JSON.toJSON(n));
        lines.get(lineNo).notes.add(n);
        System.out.println();
    }
}
