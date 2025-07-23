package com.qn.auto.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/****
 *
 * Project Name:recruit-helper-util
 * <p>随机数生成工具类,主要包括<br>
 *  中文姓名，性别，Email，手机号，住址
 */
public class RandomValueUtil {

    public static String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static String firstName =
            "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯咎管卢莫经房裘缪干解应宗宣丁贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄魏加封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘姜詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后江红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于仲孙太叔申屠公孙乐正轩辕令狐钟离闾丘长孙慕容鲜于宇文司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱晋楚阎法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟第五言福百家姓续";
    private static String girl =
            "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
    public static String boy =
            "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
    public static final String[] email_suffix = ("@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live" +
            ".com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina" +
            ".com,@sohu.com,@yahoo.com.cn").split(",");

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /***
     *
     * Project Name: recruit-helper-util
     * <p>随机生成Email
     */
    public static String getEmail(int lMin, int lMax) {
        int length = getNum(lMin, lMax);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = (int) (Math.random() * base.length());
            sb.append(base.charAt(number));
        }
        sb.append(email_suffix[(int) (Math.random() * email_suffix.length)]);
        return sb.toString();
    }

    private static String[] telFirst = ("134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153," +
            "180,181,182,183,184,185,186,187,188,189").split(",");

    /***
     *
     * Project Name: recruit-helper-util
     * <p>随机生成手机号码
     */
    public static String getTelephone() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        return getTelephone(first);
    }

    /***
     *
     * Project Name: recruit-helper-util
     * <p>随机生成手机号码
     */
    public static String getTelephone(String top3) {
        String first = top3;
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String thrid = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + thrid;
    }

    /***
     *
     * Project Name: recruit-helper-util
     * <p>随机生成4位数字的字符串
     */
    public static String getFourDigitNumberToString() {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(4);
        for(int i = 0; i < 4; i++) {
            int number = rnd.nextInt(10); // 生成一个介于 0（包括）和 10（不包括）之间的随机数
            sb.append(number);
        }
        return sb.toString();
    }

    /***
     *
     * Project Name: recruit-helper-util
     * <p>返回所有4位数字的字符串集合
     */
    public static List<String> getFourDigitNumberAll() {
        List<String> allNumbers = new ArrayList<>(10000);
        for (int i=0;i<10000;i++){
            allNumbers.add(String.format("%04d",i));
        }
        return allNumbers;
    }


    /***
     *
     * Project Name: recruit-helper-util
     * <p>随机生成8位电话号码
     */
    public static String getLandline() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String thrid = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + thrid;
    }


    /**
     * 返回中文姓名
     */
    public static String name_sex = "";

    /***
     *
     * Project Name: recruit-helper-util
     * <p>返回中文姓名
     */
    public static String getChineseName() {
        int index = getNum(0, firstName.length() - 1);
        String first = firstName.substring(index, index + 1);
        int sex = getNum(0, 1);
        String str = boy;
        int length = boy.length();
        if (sex == 0) {
            str = girl;
            length = girl.length();
            name_sex = "女";
        } else {
            name_sex = "男";
        }
        index = getNum(0, length - 1);
        String second = str.substring(index, index + 1);
        int hasThird = getNum(0, 1);
        String third = "";
        if (hasThird == 1) {
            index = getNum(0, length - 1);
            third = str.substring(index, index + 1);
        }
        return first + second + third;
    }

    /**
     * 获取随机字符串，由数字、大小写字母组成
     *
     * @param bytes：生成的字符串的位数
     * @return
     * @author
     */
    public static String getRandomStr(int bytes) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < bytes; i++) {
            //随机判断判断该字符是数字还是字母
            String choice = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(choice)) {
                //随机判断是大写字母还是小写字母
                int start = random.nextInt(2) % 2 == 0 ? 65 : 97;
                sb.append((char) (start + random.nextInt(26)));
            } else if ("num".equalsIgnoreCase(choice)) {
                sb.append(random.nextInt(10));
            }
        }
        return "autotest_"+sb.toString();
    }


    public static String getIdNo() {
        int index = getNum(0, Cities.cities.length - 1);
        boolean male = true;
        if (index > 10) {
            male = false;
        }
        //随机生成生日 1~99岁
        long begin = System.currentTimeMillis() - 3153600000000L;//100年内
        long end = System.currentTimeMillis() - 31536000000L; //1年内
        long rtn = begin + (long) (Math.random() * (end - begin));
        Date date = new Date(rtn);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String birth = simpleDateFormat.format(date);
        return getIdNo(birth, male);
    }


    public static String getIdNo(boolean male) {
        //随机生成生日 1~99岁
        long begin = System.currentTimeMillis() - 3153600000000L;//100年内
        long end = System.currentTimeMillis() - 31536000000L; //1年内
        long rtn = begin + (long) (Math.random() * (end - begin));
        Date date = new Date(rtn);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String birth = simpleDateFormat.format(date);
        return getIdNo(birth, male);
    }

    public static String getIdNo(String birth, boolean male) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int value = random.nextInt(Cities.cities.length);
        sb.append(Cities.cities[value]);
        sb.append(birth);
        value = random.nextInt(999) + 1;
        if (male && value % 2 == 0) {
            value++;
        }
        if (!male && value % 2 == 1) {
            value++;
        }
        if (value >= 100) {
            sb.append(value);
        } else if (value >= 10) {
            sb.append('0').append(value);
        } else {
            sb.append("00").append(value);
        }
        sb.append(calcTrailingNumber(sb));
        return sb.toString();
    }

    private static final int[] calcC = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final char[] calcR = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    /*
     * <p>18位身份证验证</p>
     * 根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * 第十八位数字(校验码)的计算方法为：
     * 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * 2.将这17位数字和系数相乘的结果相加。
     * 3.用加出来和除以11，看余数是多少？
     * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2。
     * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     */
    private static char calcTrailingNumber(StringBuilder sb) {
        int[] n = new int[17];
        int result = 0;
        for (int i = 0; i < n.length; i++) {
            n[i] = Integer.parseInt(String.valueOf(sb.charAt(i)));
        }
        for (int i = 0; i < n.length; i++) {
            result += calcC[i] * n[i];
        }
        return calcR[result % 11];
    }


    public static void main(String[] str) {
        System.out.println(getTelephone());
    }
}

