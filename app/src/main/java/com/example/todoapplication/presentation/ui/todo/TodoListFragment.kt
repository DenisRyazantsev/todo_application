package com.example.todoapplication.presentation.ui.todo

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapplication.R
import com.example.todoapplication.domain.entity.Todo
import com.example.todoapplication.presentation.isVisible
import com.example.todoapplication.presentation.ui.base.BaseFragment
import com.example.todoapplication.presentation.viewModel.todo.TodoListViewModel
import kotlinx.android.synthetic.main.fragment_todo_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoListFragment : BaseFragment(), TodoAdapter.OnItemClickListener {

    override val layoutRes: Int = R.layout.fragment_todo_list
    private val todoViewModel by viewModel<TodoListViewModel>()

    private var todoList = listOf<Todo>()
    private var todoAdapter = TodoAdapter(todoList)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO Зачем использовать this.viewLifecycleOwner, почему нельзя просто viewLifecycleOwner?
        //  todoViewModel -> this.todoViewModel - серьезно? Буквально строчкой выше ты пишешь по одному,
        //  а строчкой ниже по друому, в чем смысл? Ты хочешь запутать читателя, чтобы он думал, что изменилось за одну строку кода?)
        todoViewModel.getAllTodo.observe(this.viewLifecycleOwner, Observer {
            this.todoViewModel.getAllTodo.value.let { response ->
                response?.let { it ->
                    todoAdapter.updateData(it)
                    setEmptyListStatus(it)
                }
            }
        })

        // TODO А это зачем надо? В чем смысл функции если она выполняется только в одном месте во всей программе?
        //  Избавься от неё и замени телом функции, а название преврати в комментарий перед её телом.
        //  Нужно понимать зачем нужны функции, переменные.
        //  Они это просто ссылки, а в ссылке нет смысла если она применяется только в одном месте.
        //  Это просто запутывает.
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvTodos.also { rv ->
            rv.layoutManager = LinearLayoutManager(activity)
            rvTodos.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            rvTodos.adapter = todoAdapter
            todoAdapter.setOnItemClickListener(this)
        }
    }

    // TODO Вот в этой функции уже больше смысла. Ведь её ты используешь сразу в 3 местах!
    //  Вот только название врет. Какой ещё статус? Ты видимость меняешь, а не статус. Зачем запутываешь читателя?
    private fun setEmptyListStatus(todos: List<Todo>) {
        tvEmptyList.isVisible = todos.isEmpty()
    }

    // TODO Такие пустые перегруженные функции прямой признак, что что-то не так в идеи наследования.
    override fun setModelBindings() {}

    override fun setListeners() {
        btnAddTodo.setOnClickListener {
            // TODO О а это португальский? Узнаю знакомые префиксы et Title, et Description Pode ser mais específico?
             if (etTitle.text.toString().isBlank() || etDescription.text.toString().isBlank()) {
                showMessage(R.string.warning_empty_field)
            } else {
                todoViewModel.insertTodoAsync(
                    Todo(
                        null,
                        etTitle.text.toString(),
                        etDescription.text.toString()
                    )
                )

                todoViewModel.getAllTodo.observe(this.viewLifecycleOwner, Observer {
                    this.todoViewModel.getAllTodo.value.let { response ->
                        response?.let { it ->
                            todoAdapter.updateData(it)
                            setEmptyListStatus(it)
                        }
                    }
                })
                etTitle.setText("")
                etDescription.setText("")
            }
        }
    }

    // TODO Ещё одна функция, которая вызывается только в одном месте программы.
    private fun routeToDetails(todo: Todo) {
        findNavController().navigate(
            R.id.actionToTodoDetails,
            // TODO ух константный литерал, я уже видел твоего брата близнеца в соврешенно другой части программы
            bundleOf("todo" to todo)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (etTitle.text.toString().isNotBlank() && etDescription.text.toString().isNotBlank()) {
            todoViewModel.insertTodoAsync(
                Todo(
                    null,
                    // TODO Не забывай использовать trim() когда читаешь с EditText
                    etTitle.text.toString(),
                    etDescription.text.toString()
                )
            )
        }
    }

    override fun onItemClick(todo: Todo) {
        routeToDetails(todo)
    }

    override fun onDeleteItemClick(todo: Todo) {
        // TODO В чем смысл использовать let в этом месте?
        todo.let { it ->
            todoViewModel.deleteTodoAsync(it)
            todoViewModel.getAllTodo.observe(this.viewLifecycleOwner, Observer {
                this.todoViewModel.getAllTodo.value.let {
                    it?.let { response ->
                        todoAdapter.updateData(response)
                        setEmptyListStatus(response)
                    }
                }
            })
        }
    }
}
