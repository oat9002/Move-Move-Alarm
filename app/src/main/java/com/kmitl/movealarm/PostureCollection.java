package com.kmitl.movealarm;

import android.content.Context;

import com.kmitl.movealarm.Model.Posture;

import java.util.ArrayList;

/**
 * Created by Administrator on 24/10/2558.
 */
public class PostureCollection {

    private static PostureCollection instance = null;
    private ArrayList<Posture> collection = new ArrayList<>();
//    private static int count=0;
    private PostureCollection(){

    }
    public static PostureCollection getInstance(Context context){
        if (instance == null) {
            instance = new PostureCollection();
            instance.initial(context);
        }
        return instance;
    }

    public void addImage(int idPosture,int vdo,int image,String name,String description,int mode,Context context){
        Posture posture = new Posture(idPosture,vdo,image,name,description,mode);
        posture.save(context);
        collection.add(posture);
    }

    public void initial (Context context){
        if(Posture.getPostureCount(context)<13) {
            addImage(1,R.raw.vpos1_1,R.drawable.pos1_1,"1.ยืดกล้ามเนื้อคอด้านข้าง", "นั่งบนเก้าอี้หรือเบาะลำตัวตรงห้ามขยับ ค่อยๆหันศีรษะไปทางด้านขวา ให้รู้สึกว่าตึงกล้ามเนื้อคอด้านซ้าย ค้างไว้นับในใจ 10 – 15 วินาที จากนั้นหันกลับมาอยู่ในท่าหน้าตรงแล้วค่อยๆหันไปทางด้านซ้ายให้รู้สึกว่าตึงกล้ามเนื้อคอด้านขวา ค้างไว้นับในใจ 10 -15 วินาที เช่นกัน",1,context);
            addImage(2,R.raw.vpos1_2,R.drawable.pos1_2,"2.ยืดกล้ามเนื้อคอด้านข้าง", "นั่งบนเก้าอี้หรือเบาะลำตัวตรงห้ามขยับ ค่อยๆเอียงศีรษะไปทางด้านขวา ให้รู้สึกว่าตึงกล้ามเนื้อคอด้านซ้าย ค้างไว้นับในใจ 10 – 15 วินาที จากนั้นหันกลับมาอยู่ในท่าหน้าตรงแล้วค่อยๆเอียงคอไปทางด้านซ้ายให้รู้สึกว่าตึงกล้ามเนื้อคอด้านขวา ค้างไว้นับในใจ 10 -15 วินาที เช่นกัน",1,context);
            addImage(3,R.raw.vpos1_3,R.drawable.pos1_3,"3.ยืดกล้ามเนื้อคอด้านหน้าและด้านหลัง", "นั่งบนเก้าอี้หรือเบาะลำตัวตรงห้ามขยับ ประสานมือไว้ด้านหน้าจากนั้นใช้นิ้วหัวหัวแม่มือดันคางให้หน้าเงยขึ้นไปด้านบน ให้รู้สึกตึงคอด้านหน้า ค้างไว้นับในใจ 10 – 15 วินาที จากนั้นกลับมาอยู่ในท่าหน้าตรงแล้วประสานมือเช่นเดิมนำไปจับที่ท้ายทอยแล้วค่อยๆกดท้ายทอยให้อยู่ในท่าก้มหัว ให้รู้สึกว่าตึงกล้ามเนื้อคอด้านหลัง ค้างไว้นับในใจ 10 – 15 วินาที",1,context);
            addImage(4,R.raw.vpos2_1,R.drawable.pos2_1,"4.กายบริหาร ยืดกล้ามเนื้อหัวไหล่", "นั่งบนเก้าอี้หรือเบาะลำตัวตรงห้ามขยับ กางแขนออกแล้วงอแขนนำมือมาจับที่หัวไหล่ หมุนหัวไหล่ไปด้านหน้า นับในใจ 10 – 15 ครั้ง จากนั้น หมุนหัวไหล่ไปด้านหลัง นับในใจ 10 – 15 ครั้ง",2,context);
            addImage(5,R.raw.vpos2_2,R.drawable.pos2_2,"5.ยืดกล้ามเนื้อไหล่และสะบัก", "นั่งบนเก้าอี้หรือเบาะลำตัวตรงห้ามขยับ ยืดแขนขวาไปด้านหน้าแล้วงอแขนขวาไปด้านซ้ายจับที่บ่าด้านซ้ายแล้วนำมือซ้ายจับที่ข้อศอกของแขนด้านขวาดันเข้าหาตัวให้รู้สึกว่าตึงหัวไหล่และสะบัก นับในใจค้างไว้ 10 – 15 วินาที กลับมาอยู่ในท่านั่งตัวตรงแล้วสลับไปทำด้านซ้ายโดยยืดแขนซ้ายไปด้านหน้าแล้วงอแขนซ้ายไปด้านขวา จับที่บ่าด้านขวาแล้วนำมือขวาจับที่ข้อศอกของแขนด้านซ้ายดันเข้าหาตัวให้รู้สึกว่าตึงหัวไหล่และสะบัก นับในใจค้างไว้ 10 – 15 วินาที",2,context);
            addImage(6,R.raw.vpos4_1,R.drawable.pos4_1,"6.ยืดกล้ามเนื้อต้นแขนด้านหลัง", "นั่งบนเก้าอี้หรือเบาะลำตัวตรงห้ามขยับ ยกแขนขวาขึ้นด้านบนงอแขนขวาไปแตะกลางหลังส่วนบน แล้วนำมือซ้าย จับที่ข้อศอกขวาแล้วดึงไปทางซ้าย ให้รู้สึกว่าตึงช่วงต้นแขนด้านหลัง นับในใจค้างไว้ 10 – 15 วินาที กลับมาอยู่ในท่านั่งตัวตรงแล้วสลับไปทำด้านซ้าย ยกแขนซ้ายขึ้นด้านบนงอแขนซ้ายไปแตะกลางหลังส่วนบน แล้วนำมือขวา จับที่ข้อศอกซ้ายแล้วดึงไปทางขวา ให้รู้สึกว่าตึงช่วงต้นแขนด้านหลัง นับในใจค้างไว้ 10 – 15 วินาที",4,context);
            addImage(7,R.raw.vpos5_1,R.drawable.pos5_1,"7.ยืดกล้ามเนื้ออก", "ยืนลำตัวตรง กางขากว้างประมาณหัวไหล่ ประสานมือไว้ด้านหลังแขนตึงยกแขนขึ้นไปทางด้านหลัง ให้รู้สึกว่าตึงหน้าอก นับในใจค้างไว้ 10 – 15 วินาที",5,context);
            addImage(8,R.raw.vpos3_1,R.drawable.pos3_1,"8.ยืดกล้ามเนื้อลำตัว", "ยืนลำตัวตรง ยืดแขนทั้งสองข้างไปด้านประสานมือแล้วบิดมือที่ประสานให้ฝ่ามืออยู่ด้านหน้า ยกแขนขึ้นไปด้านหลังศีรษะห้ามงอแขน ให้รู้สึกว่าตึงกล้ามเนื้อลำตัว นับในใจค้างไว้ 10 – 15 วินาที",3,context);
            addImage(9,R.raw.vpos4_2,R.drawable.pos4_2,"9.ยืดกล้ามเนื้อปลายแขนด้านหน้าและปลายแขนด้านหลัง", "ยืนตัวตรง กางขาประมาณหัวไหล่ ยืดแขนซ้ายไปด้านหน้าคว่ำมือลง ใช้มือขวาจับฝ่ามืองอขึ้น ให้รู้สึกว่าตึงกล้ามเนื้อปลายแขนด้านหน้า นับในใจค้างไว้ 10 – 15 วินาที จากนั้นอยู่ในท่าคว่ำมือแขนเหยียดไปด้านหน้า ใช้มือขวาจับหลังมือซ้าย กดมือลงไปด้านล่างให้รู้สึกว่าตึงกล้ามเนื้อปลายแขนด้านหลัง นับในใจค้างไว้ 10 – 15 วินาที กลับมาอยู่ในท่ายืนตรง แล้วยืดแขนซ้ายไปด้านหน้าคว่ำมือลง ใช้มือขวาจับฝ่ามืองอขึ้น ให้รู้สึกว่าตึงกล้ามเนื้อปลายแขนด้านหน้า นับในใจค้างไว้ 10 – 15 วินาที จากนั้นอยู่ในท่าคว่ำมือแขนเหยียดไปด้านหน้า ใช้มือขวาจับหลังมือซ้าย กดมือลงไปด้านล่างให้รู้สึกว่าตึงกล้ามเนื้อปลายแขนด้านหลัง นับในใจค้างไว้ 10 – 15 วินาที",4,context);
            addImage(10,R.raw.vpos3_2,R.drawable.pos3_2,"10.ยืดกล้ามเนื้อลำตัวด้านข้าง", "ยืนตัวตรง กางขาประมาณหัวไหล่ นำมือทั้งสองข้างท้าวสะเอวแล้วบิดตัวไปทางด้านขวา ให้รู้สึกว่าตึงกล้ามเนื้อลำตัวด้านซ้าย นับในใจค้างไว้ 10 – 15 วินาที แล้วบิดลำตัวไปทางด้านซ้าย ให้รู้สึกว่าตึงกล้ามเนื้อลำตัวด้านขวา นับในใจค้างไว้ 10 – 15 วินาที",3,context);
            addImage(11,R.raw.vpos6_1,R.drawable.pos6_1,"11.กายบริหาร เหวี่ยงเท้าหน้าและหลัง", "ยืนตัวตรง กางขาประมาณหัวไหล่ ใช้อุปกรณ์ เช่น เก้าอี้ เพื่อจับไว้ไม่ให้เสียการทรงตัว เริ่มจากขาซ้ายโดยการใช้มือขวาจับที่เก้าอี้จากด้านขวาแล้วยกขาซ้าย ห้ามงอเข่า เหวี่ยงไปทางข้างหน้าและข้างหลังสลับกัน นับในใจ 10 – 15 ครั้ง กลับมาอยู่ในท่ายืนตัวตรง ใช้มือซ้ายจับที่เก้าอี้จากด้านซ้ายแล้วยกขาขวา ห้ามงอเข่า เหวี่ยงไปทางข้างหน้าและข้างหลังสลับกัน นับในใจ 10 – 15 ครั้ง",6,context);
            addImage(12,R.raw.vpos6_2,R.drawable.pos6_2,"12.ยืดกล้ามเนื้อต้นขาด้านหน้า", "ยืนตัวตรง กางขาประมาณหัวไหล่ ใช้อุปกรณ์เช่น เก้าอี้ เพื่อจับไว้ไม่ให้เสียการทรงตัว เริ่มจากขาซ้ายโดยการใช้มือขวาจับที่เก้าอี้จากด้านขวา แล้วงอขาซ้ายไปด้านหลังใช้มือซ้ายจับที่หลังเท้าดึงเข้าหาสะโพก ให้รู้สึกว่าตึงกล้ามเนื้อต้นขาด้านหน้านับในใจค้างไว้ 10 – 15 วินาที กลับมาอยู่ในท่าตรง ใช้มือซ้ายจับที่เก้าอี้จากด้านซ้าย แล้วงอขาขวาไปด้านหลังใช้มือขวาจับที่หลังเท้าดึงเข้าหาสะโพก ให้รู้สึกว่าตึงกล้ามเนื้อต้นขาด้านหน้านับในใจค้างไว้ 10 – 15 วินาที",6,context);
            addImage(13,R.raw.vpos6_4,R.drawable.pos6_4,"13.กายบริหาร ยืดกล้ามเนื้อหน้าแข้งและกล้ามเนื้อน่อง", "ยืนตัวตรง กางขาประมาณหัวไหล่ ยืนเขย่งปลายเท้าทั้งสองข้างให้รู้สึกว่าตึงน่อง แล้วยืนยกปลายเท้าขึ้นให้รู้สึกตึงกล้ามเนื้อหน้าแข้งสลับกัน นับในใจค้างไว้ 10 – 15 ครั้ง",6,context);
            addImage(14,R.raw.vpos6_5,R.drawable.pos6_5,"14.กายบริหาร ยืดกล้ามเนื้อหน้าแข้งและกล้ามเนื้อน่อง", "ยืนตัวตรง กางขาประมาณหัวไหล่ ใช้อุปกรณ์เช่น เก้าอี้ เพื่อจับไว้ไม่ให้เสียการทรงตัว เริ่มจากขาซ้ายโดยการใช้มือขวาจับที่เก้าอี้จากด้านขวา ยกขาซ้ายเหยียดตึงไปด้านหน้าประมาณ 45 องศา แล้วกระดกปลายเท้าขึ้นลง ให้รู้สึกตึงกล้ามเนื้อหน้าแข้งและกล้ามเนื้อน่อง นับในใจ 10 – 15 ครั้ง กลับมาอยู่ในท่าตรง ใช้มือซ้ายจับที่เก้าอี้จากด้านซ้าย ยกขาขวาเหยียดตึงไปด้านหน้าประมาณ 45 องศา แล้วกระดกปลายเท้าขึ้นลง ให้รู้สึกตึงกล้ามเนื้อหน้าแข้งและกล้ามเนื้อน่อง นับในใจ 10 – 15 ครั้ง ",6,context);
            addImage(15,R.raw.vpos6_7,R.drawable.pos6_7,"15.ยืดกล้ามเนื้อน่อง", "ยืนตัวตรง ก้าวเท้าซ้ายไปด้านหลังพอประมาณ งอเข่าขวาพร้อมกับเอียงตัวไปด้านหน้าแล้วนำมือทั้งสองข้างมาประสานที่หัวเข่าด้านขวา ให้รู้สึกว่าตึงกล้ามเนื้อน่อง นับในใจค้างไว้ 10 – 15 วินาที กลับมาอยู่ในท่าตรง ก้าวเท้าขวาไปด้านหลังพอประมาณ งอเข่าซ้ายพร้อมกับเอียงตัวไปด้านหน้าแล้วนำมือทั้งสองข้างมาประสานที่หัวเข่าด้านซ้าย ให้รู้สึกว่าตึงกล้ามเนื้อน่อง นับในใจค้างไว้ 10 – 15 วินาที",6,context);
            addImage(16,R.raw.vpos5_2,R.drawable.pos5_2,"16.กายบริหาร กล้ามเนื้อหน้าท้อง", "นั่งเก้าอี้ ใช้มือทั้งสองข้างจับที่กั้นข้างเก้าอี้ แล้วค่อยๆยกเข่าขวาสลับกับเข่าซ้าย นับในใจ 10 – 15 ครั้ง",5,context);
            addImage(17,R.raw.vpos6_10,R.drawable.pos6_10,"17.กายบริหาร เหวี่ยงเท้าหน้าและหลัง", "ยืนตัวตรง กางขาประมาณหัวไหล่ ใช้อุปกรณ์เช่น เก้าอี้ เพื่อจับไว้ไม่ให้เสียการทรงตัว เริ่มจากขาซ้ายโดยการใช้มือขวาจับที่เก้าอี้จากด้านขวาแล้วยกเข่าซ้ายไปทางข้างหน้าและเหวี่ยงขาตรงกลับไปด้านหลัง นับในใจ 10 – 15 ครั้ง กลับมาอยู่ในท่ายืนตัวตรง ใช้มือซ้ายจับที่เก้าอี้จากด้านซ้ายแล้วยกเข่าขวาไปทางข้างหน้าและเหวี่ยงขาตรงกลับไปด้านหลัง นับในใจ 10 – 15 ครั้ง",6,context);
            addImage(18,R.raw.vpos6_3,R.drawable.pos6_3,"18.ยืดกล้ามเนื้อต้นขาด้านหลัง", "ยืนตัวตรง กางขาประมาณหัวไหล่ ก้มนำมือแตะพื้นระหว่างปลายเท้า ห้ามงอเข่าหลังตรง ให้รู้สึกตึงกล้ามเนื้อต้นขาด้านหลัง นับในใจค้างไว้ 10 -15 วินาที",6,context);
            addImage(19,R.raw.vpos3_3,R.drawable.pos3_3,"19.ยืดกล้ามเนื้อด้านข้างลำตัว", "ยืนตัวตรง กางขาประมาณหัวไหล่ ยืดแขนทั้งสองข้างไปด้านหน้าประสานมือแล้วบิดมือที่ประสานให้ฝ่ามืออยู่ด้านหน้า ยกแขนขึ้นไปด้านหลังศีรษะห้ามงอแขน แล้วเอียงตัวไปทางด้านขวา ให้รู้สึกตึงลำตัวข้างซ้าย นับในใจ 10 – 15 วินาที กลับมาอยู่ในท่าตรง ยืดแขนทั้งสองข้างไปด้านประสานมือแล้วบิดมือที่ประสานให้ฝ่ามืออยู่ด้านหน้า ยกแขนขึ้นไปด้านหลังศีรษะห้ามงอแขน แล้วเอียงตัวไปทางด้านซ้าย ให้รู้สึกตึงลำตัวข้างขวา นับในใจ 10 – 15 วินาที",3,context);
            addImage(20,R.raw.vpos6_11,R.drawable.pos6_11,"20.ยืดกล้ามเนื้อต้นขาด้านหลัง", "นั่งเก้าอี้ตัวตรง เหยียดขาซ้ายไปด้านหน้าส้นเท้าติดพื้น ก้มพร้อมค่อยๆนำมือซ้ายไปแตะที่ปลายเท้าซ้าย ห้ามงอเข่า ให้รู้สึกว่าตึงกล้ามเนื้อต้นขาด้านหลัง นับในใจ 10 – 15 วินาที กลับมาอยู่ในท่านั่งตัวตรง เหยียดขาขวาไปด้านหน้าส้นเท้าติดพื้น ก้มพร้อมค่อยๆนำมือขวาไปแตะที่ปลายเท้าขวา ห้ามงอเข่า ให้รู้สึกว่าตึงกล้ามเนื้อต้นขาด้านหลัง นับในใจ 10 – 15 วินาที",6,context);
            addImage(21,R.raw.vpos6_8,R.drawable.pos6_8,"21.กายบริหาร ยืดกล้ามเนื้อหน้าแข้งและกล้ามเนื้อน่อง", "ยืนตัวตรง กางขาประมาณหัวไหล่ ใช้อุปกรณ์เช่น เก้าอี้ เพื่อจับไว้ไม่ให้เสียการทรงตัว ยืนจับเก้าอี้สองมือ เขย่งปลายเท้าทั้งสองข้างให้รู้สึกว่าตึงน่อง แล้วยืนยกปลายเท้าขึ้นให้รู้สึกว่าตึงกล้ามเนื้อหน้าแข้งสลับกัน นับในใจค้างไว้ 10 – 15 ครั้ง",6,context);
            addImage(22,R.raw.vpos6_9,R.drawable.pos6_9,"22.กายบริหาร ยืดกล้ามเนื้อหน้าแข้งและกล้ามเนื้อน่อง", "นั่งบนเก้าอี้หลังตรง เริ่มจากขาซ้ายโดยการใช้มือทั้งสองข้างจับที่กั้นเก้าอี้ด้านข้าง ยกขาซ้ายเหยียดตึงไปด้านหน้าประมาณ 45 องศา แล้วกระดกปลายเท้าขึ้นลง ให้รู้สึกตึงกล้ามเนื้อหน้าแข้งและกล้ามเนื้อน่อง นับในใจ 10 – 15 ครั้ง กลับมาอยู่ในท่าตรง ใช้มือทั้งสองข้างจับที่กั้นเก้าอี้ด้านข้าง ยกขาขวาเหยียดตึงไปด้านหน้าประมาณ 45 องศา แล้วกระดกปลายเท้าขึ้นลง ให้รู้สึกตึงกล้ามเนื้อหน้าแข้งและกล้ามเนื้อน่อง นับในใจ 10 – 15 ครั้ง ",6,context);
            addImage(23,R.raw.vpos5_3,R.drawable.pos5_3,"23.ยืดกล้ามเนื้อหลัง", "ยืนตัวตรง กางขาประมาณหัวไหล่ ใช้อุปกรณ์เช่น เก้าอี้ เพื่อจับไว้ไม่ให้เสียการทรงตัว นำมือทั้งสองข้างจับด้านหลังเก้าอี้ แล้วก้มตัวลงให้หลังตรงขนานกับพื้นขาเหยียดตรง ให้รู้สึกว่าตึงกล้ามเนื้อหลัง นับในใจค้างไว้ 10 – 15 วินาที",5,context);
            addImage(24,R.raw.vpos3_4,R.drawable.pos3_4,"24.ยืดกล้ามเนื้อด้านข้างลำตัว", "นั่งเก้าอี้หลังตรง ยืดแขนทั้งสองข้างไปด้านหน้าประสานมือแล้วบิดมือที่ประสานให้ฝ่ามืออยู่ด้านหน้า ยกแขนขึ้นไปด้านหลังศีรษะห้ามงอแขน แล้วเอียงตัวไปทางด้านขวา ให้รู้สึกตึงลำตัวข้างซ้าย นับในใจ 10 – 15 วินาที กลับมาอยู่ในท่าตรง ยืดแขนทั้งสองข้างไปด้านประสานมือแล้วบิดมือที่ประสานให้ฝ่ามืออยู่ด้านหน้า ยกแขนขึ้นไปด้านหลังศีรษะห้ามงอแขน แล้วเอียงตัวไปทางด้านซ้าย ให้รู้สึกตึงลำตัวข้างขวา นับในใจ 10 – 15 วินาที",3,context);
            addImage(25,R.raw.vpos3_5,R.drawable.pos3_5,"25.กายบริหาร ยืดกล้ามเนื้อลำตัวด้านข้าง", "ยืนตัวตรง กางขาประมาณหัวไหล่ ใช้อุปกรณ์เช่น เก้าอี้ เพื่อจับไว้ไม่ให้เสียการทรงตัว ใช้มือขวาจับเก้าอี้ยกขาซ้ายไปด้านข้างลำตัว ขึ้นลง นับในใจ 10 – 15 ครั้ง กลับมาอยู่ในท่าตรง ใช้มือซ้ายจับเก้าอี้ยกขาขวาไปด้านข้างลำตัวขึ้นลง นับในใจ 10 – 15 ครั้ง",3,context);
            addImage(26,R.raw.vpos6_6,R.drawable.pos6_6,"26.ยืดกล้ามเนื้อหน้าแข้ง", "ยืนตัวตรง กางขาประมาณหัวไหล่ ใช้อุปกรณ์เช่น เก้าอี้ เพื่อจับไว้ไม่ให้เสียการทรงตัว ใช้มือขวาจับเก้าอี้ ก้าวขาซ้ายไปด้านหน้าเอียงเท้าไปทางด้านซ้ายกดลง ให้รู้สึกว่าตึงกล้ามเนื้อหน้าแข้ง นับในใจ 10 – 15 วินาที กลับมาอยู่ในท่าตรง ใช้มือซ้ายจับเก้าอี้ ก้าวขาขวาไปด้านหน้าเอียงเท้าไปทางด้านขวากดลง ให้รู้สึกว่าตึงกล้ามเนื้อหน้าแข้ง นับในใจ 10 – 15 วินาที",6,context);

        }
        else{
            for(int i=0;i<Posture.getPostureCount(context);i++){
                Posture posture=Posture.find(i,context);
                collection.add(posture);
            }

        }

    }
    public int size(){
        return collection.size();
    }
    public Posture getPosture(int id){
        return collection.get(id);
    }
    public ArrayList<Posture> getPosture(int[] id){
        ArrayList<Posture> imgcoll = new ArrayList<>();
        for (int i=0;i<id.length;i++){
            imgcoll.add(collection.get(id[i]));
        }
        return imgcoll;
    }
    public ArrayList<Posture> getPostureMode(int mode,Context context){
        ArrayList<Posture> imgcoll = Posture.findMode(mode,context);
        return  imgcoll;

    }




}
