package com.example.todoapplication.presentation.viewModel.todo

import androidx.lifecycle.ViewModel
import com.example.todoapplication.domain.dao.TodoDao
import com.example.todoapplication.domain.entity.Todo
import com.example.todoapplication.domain.repository.TodoRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class TodoListViewModel(
    private val todoRepository: TodoRepository,
    private val todoDao: TodoDao
) : ViewModel() {
    val getAllTodo = todoRepository.getAllTodo

    // TODO Почему во ViewModel функции, которые должны быть в DAO или в репозитории?
    fun deleteTodoAsync(todo: Todo) = GlobalScope.async {
        todoDao.delete(todo)
    }

    fun insertTodoAsync(todo: Todo) = GlobalScope.async {
        todoRepository.insert(todo)
    }
}