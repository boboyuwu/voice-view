# voiceview

自定义的音量控制控件挺好用的基本没什么Bug使用一个大布局包裹，<br/>
它最右侧留有一个控制图标的位置可以放置你自己的图标,使用如下图代码所示<br/>

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:id="@+id/activity_main"
    android:layout_width="100dp"
                android:layout_height="30dp"
    tools:context="com.boboyuwu.voiceviewdemo.MainActivity">

    <com.boboyuwu.voiceviewdemo.VoiceView
        android:id="@+id/vv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

    <Button
        android:layout_width="30dp"
        android:background="@mipmap/speed_img"
        android:layout_alignParentRight="true"
        android:id="@+id/btn"
        android:layout_height="30dp"/>

</RelativeLayout> <br/><br/>

这是布局，直接调用setOnSpeedClickListener（）方法可以设置监听得到速度<br/>
直接调用setSpeedLength()可以直接设置我们音量的大小值 <br/>
mVoiceView.setRightImage(R.mipmap.speed_img);可以设置右边图片，传null或者不调用这个方法默认不显示<br/>
我们看一下效果图<br/>

![Image text](https://github.com/boboyuwu/pic/blob/master/pic1.png) <br/>

