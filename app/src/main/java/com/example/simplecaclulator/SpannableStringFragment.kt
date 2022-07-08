package com.example.simplecaclulator

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class SpannableStringFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spannable_string, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spanString = SpannableString(getString(R.string.opencalculator))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                Log.d(TAG, "onClick: SpannableString")
                val calculatorFragment = CalculatorFragment.newInstance()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, calculatorFragment).addToBackStack(null)
                    .commit()
            }
        }
        spanString.setSpan(clickableSpan, 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spanString.setSpan(
            RelativeSizeSpan(3.0f),
            0,
            spanString.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        view.findViewById<TextView>(R.id.SpanStringTV).apply {
            text = spanString
            movementMethod = LinkMovementMethod()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}