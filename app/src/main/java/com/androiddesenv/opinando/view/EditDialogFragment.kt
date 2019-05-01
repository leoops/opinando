package com.androiddesenv.opinando.view

import android.arch.lifecycle.ViewModelProviders
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.androiddesenv.opinando.model.Review
import com.androiddesenv.opinando.model.ReviewRepository
import com.androiddesenv.opinando.viewModel.EditReviewViewModel
import com.androiddesenv.opinando.R

class EditDialogFragment: DialogFragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.new_review_form_layout, null)
        populateView(view)
        configureSaveButton(view)
        return view
    }
    private fun configureSaveButton(view: View) {
        val textName = view.findViewById<EditText>(R.id.product_name_editText)
        val textReview = view.findViewById<EditText>(R.id.opinion_editText)
        val button = view.findViewById<Button>(R.id.record_button)
        val viewModel = ViewModelProviders.of(activity!!).get(EditReviewViewModel::class.java)
        var review = viewModel.data.value!!
        button.setOnClickListener {
            val review = Review(review.id, textName.text.toString(), textReview.text.toString(), null, null, null, null)
            object: AsyncTask<Void, Void, Unit>(){
                override fun doInBackground(vararg params: Void?) {
                    ReviewRepository(activity!!.applicationContext).update(review.id, review.name, review.review)
                }

                override fun onPostExecute(result: Unit?) {
                    viewModel.data.value = review
                    dismiss()
                }

            }.execute()
        }
    }

    override fun onResume() {
        val params = dialog.window.attributes.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        dialog.window.attributes = params
        super.onResume()
    }

    private fun populateView(view: View) {
        val review = ViewModelProviders.of(activity!!).get(EditReviewViewModel::class.java).data.value
        view.findViewById<EditText>(R.id.product_name_editText).setText(review!!.name)
        view.findViewById<EditText>(R.id.opinion_editText).setText(review!!.review)
    }

}