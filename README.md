**Dynamic Source**  
这是一个类似遇见中的动态的源码.

其中只有一个Activity-MainActivity.   
*其中比较值得关注的是*    
1.	MultiImageView这个View,这个用于显示1~N张图片的一个View.   
2.	CommentItemView这个View,用来显示单条评论,其中可以点击名字与整个View.其中的点击事件没有实现   
3.	点击评论时候,会定位到单条动态的最底部,且弹出键盘

**示意图**
 
 ![image](https://github.com/Kevinsu917/Dynamic/blob/master/pic.png)


**注意**: 之前一直少了一个下拉刷新的库,导致很多朋友都没法运行,实在不好意思.现在把该库以zip包的模式放在根目录下.