package com.example.todoapplication.presentation.ui.todo

import android.os.Bundle
import android.view.View
import com.example.todoapplication.R
import com.example.todoapplication.domain.entity.Todo
import com.example.todoapplication.presentation.ui.base.BaseFragment
import com.example.todoapplication.presentation.viewModel.todo.TodoDetailsViewModel
import kotlinx.android.synthetic.main.fragment_todo_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoDetailsFragment : BaseFragment() {

    // TODO Зачем это? Почему не использовать Fragment(idLayout)?
    override val layoutRes: Int = R.layout.fragment_todo_details
    private val todoDetailsViewModel by viewModel<TodoDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO Литерная константа это зло. Во первых я понятия не имею, что это, а название переменной бы мне это объяснило.
        //  Во вторых ты если изменишь её в будущем в одном месте, то потеряешь в другом, ведь это тэг для бандла между двумя фрагментами.
        //  И опять, зачем вообще создается эта переменная? Ты её сразу же выбрасываешь сборщику мусора, нигде не переиспользуешь, в ней просто нет смысла.
        val todo = arguments?.get("todo") as? Todo

        todo?.let { it ->
            tvTitle.text = it.title
            tvDescription.text = it.description
        }
    }
    // TODO И зачем было наследовать? Хоть укажи, что не просто забыл написать код, а что так и было задумано. Добавь комментарий внутрь тела функции Nothing.
    override fun setModelBindings() {}

    override fun setListeners() {}
}
