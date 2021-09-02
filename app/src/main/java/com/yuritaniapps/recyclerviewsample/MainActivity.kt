package com.yuritaniapps.recyclerviewsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.MutableList

/**
 * この実装に関する参考情報:
 * 1.
 * https://developer.android.com/guide/topics/ui/layout/recyclerview?hl=ja
 * 2.
 * https://github.com/android/views-widgets-samples/tree/main/RecyclerViewKotlin
 * 3.
 * https://stackoverflow.com/questions/39160806/linearlayoutmanager-vs-gridlayoutmanager
 * 4.
 * https://qiita.com/morimonn/items/035b1d85fec56e64f3e1
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // テスト用の文字列を生成
        val list = mutableListOf<ListItemDataModel>()
        for(i in 1..20){
            list.add(ListItemDataModel("タイトル$i", "説明文$i"))
        }

        // activity_main.xmlのレイアウトファイルから，RecyclerViewを取得する．
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // 取得したRecyclerViewに，下に作成したAdapterを登録する．
        recyclerView.adapter = RecyclerViewAdapter(list)

        // 作成したリストをどのように表示するかを決めるために，layoutManagerを登録する．
        // 第2引数にLinearLayoutManagerの値を指定することで表示形式をしていするようだ．
        // LinearLayoutManager.VERTICAL ... 縦にリストを表示
        // LinearLayoutManager.HORIZONTAL ... 横にリストを表示
        // その他，詳細はわからないが，
        // GridLayoutManagerは，縦横の碁盤目状にリストを表示する．
        // StaggeredGridLayoutManagerは，縦横にリストを表示するが，高さや幅はばらばらでもよい．
        // 参考文献は1. と3.
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 今回は，リストの要素1つひとつがわかりやすくなるように，区切り線を付ける．
        // 参考文献は4.
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }
}

/**
 * RecyclerViewを生成するために使うクラス．
 * ここでは，
 * データ1つ分を表示するためのレイアウトファイルをViewオブジェクトとして読み込むonCreateViewHolderメソッド，
 * RecyclerViewで使用するデータ1つ分のレイアウトのViewを扱うViewHolderクラス，
 * ViewHolderのレイアウト要素に実際のデータをセットするonBindViewHolderメソッドを実装する．
 * @param dataSet RecyclerViewに表示したいデータのリスト．1アイテムが複数種類のデータを扱う場合，別途それらをメンバ変数として一つのクラスにまとめ，それをListオブジェクトにしたものを渡すとよい．
 */
class RecyclerViewAdapter (private val dataSet: MutableList<ListItemDataModel>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    /**
     * コンストラクタの引数には，データ一つ分を格納するレイアウトファイルのViewオブジェクトが送られてくる．
     * 送られてきたViewオブジェクトから，データを格納するレイアウト要素を，findViewByIdメソッドを用いて取得し，データをセットする．
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mainTextView: TextView = view.findViewById(R.id.mainTextView)
        val subTextView: TextView = view.findViewById(R.id.subTextView)
    }

    /**
     * データ1つ分を表示するレイアウトファイルを，Viewオブジェクトとしてインフレートする．
     * そのViewオブジェクトをViewHolderに渡して作ったインスタンスを返す．
     */
    @Override
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_row, viewGroup, false)
        return ViewHolder(view)
    }

    /**
     * onCreateViewHolderで生成されたViewHolderのレイアウト要素に，実際の1行分のデータをセットするメソッド．
     */
    @Override
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){
        val oneData = dataSet[position]
        viewHolder.mainTextView.text = oneData.mainText
        viewHolder.subTextView.text = oneData.subText
    }

    override fun getItemCount() = dataSet.size

}

/**
 * RecyclerViewで使うためのデータモデル．
 * @param mainText list_row.xmlにおける上側の大きい方のTextViewに入る文字列を格納する．
 * @param subText list_row.xmlに置ける下側の小さい方のTextViewに入る文字列を格納する．
 */
class ListItemDataModel(val mainText: String, val subText: String){}