package com.example.todoapplication.presentation.ui.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R
import com.example.todoapplication.domain.entity.Todo
import kotlinx.android.synthetic.main.item_todo_list.view.*

// TODO Советую задуматься об увеличении допустимого количества символов в строке до 120 символов.
//  Современное разрешение мониторов позволяет такую роскошь.
internal class TodoAdapter(var todoList: List<Todo>) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(todo: Todo)
        fun onDeleteItemClick(todo: Todo)
    }

    // TODO Это что такое??? Мы в питоне? Зачем использовать нижнее подчеркивание перед названием переменной?
    //  Его можно использовать только когда у тебя две переменных с одинаковым именем и одна из них в публичном API, а другая в закрытом, чтобы их различать.
    //  В Питоне это указывает на модификатор типа, не в Koltin'e (и в Java), у нас для этого есть слово private и public.
    private var _onItemClickListener: OnItemClickListener? = null

    // TODO Почему нельзя использовать прямой доступ к переменой?
    //  Зачем функция, которая занимается только присвоением значения и ничем кроме?
    //  И даже если бы занималась в Koltin'е принято такие вещи делать через get() и set() у переменной.
    fun setOnItemClickListener(onItemClickListener: TodoListFragment) {
        _onItemClickListener = onItemClickListener
    }

    // TODO Ну вот опять, из-за ограничения в 100 символов вместо кода получается лесенка
    //  где надо сломать глаза, чтобы понять, что написано.
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        // TODO Где используется ещё переменная itemView? Какой был смысл её создавать, а не присвоить значение сразу в конструктор ViewHolder?
        //  Переменные нужны, чтобы "хранить" значения и использовать одну ссылку на эту информацию во многих местах.
        //  Иногда в них есть смысл когда написана сложная логика и её нужно как-то разделить даже если можно было написать в теле уравнении сразу.
        //  Но какой смысл в создании абстракции, которая нужна для хранения если ты сразу же её используешь, а потом отправляешь сборщику мусора?
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_todo_list, viewGroup, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val data = todoList[i]

        // TODO Эх, ну вот что такое tvTitle? Это про TV как про телевидение?
        //  Экономия для тебя трёх символов, а для другого разработчика боль и догадки.
        viewHolder.tvTitle.text = data.title
        viewHolder.itemView.tvTitle.setOnClickListener {
            _onItemClickListener?.onItemClick(data)
        }
        viewHolder.itemView.btnDelete.setOnClickListener {
            _onItemClickListener?.onDeleteItemClick(data)
        }
    }

    fun updateData(data: List<Todo>) {
        this.todoList = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = todoList.size

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    }
}