package com.example.todoapplication.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todoapplication.presentation.inflate
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    abstract val layoutRes: Int

    // TODO Читай раздел [https://kotlinlang.org/docs/reference/functions.html#single-expression-functions].
    //  Использовать = для методов можно только если "всё" выражение помещается в одну строчку.
    //  Окей, иногда можно, чтобы и в две и в три, но точно не в 8 строк кода. Используй скобки.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        container?.inflate(layoutRes).also {
            setHasOptionsMenu(true)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setModelBindings()
        setListeners()
    }

    abstract fun setModelBindings()

    abstract fun setListeners()

    // TODO Замени функцию внутри на showMessage(String) вызывав метод getString(resMsg) как аругмент.
    //  Иначе это дублирование кода. И ты уверен, что нельзя Snackbar создать как-то не используя переменную view?
    //  А то выглядит немного странно.
    open fun showMessage(resMsg: Int) {
        val view = view ?: return
        Snackbar.make(view, resMsg, Snackbar.LENGTH_SHORT).show()
    }

    open fun showMessage(msg: String) {
        val view = view ?: return
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
}
