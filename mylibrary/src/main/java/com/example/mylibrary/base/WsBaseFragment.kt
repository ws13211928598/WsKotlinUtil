package com.example.mylibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 *  created by ws
 *   on 2021/6/2
 *   describe:
 */
abstract class WsBaseFragment<VB : ViewBinding> : Fragment() {
    private  var _binding: VB? = null
    protected val binding get() = _binding
    var TAG = "asdws"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //FragmentHomeBinding.inflate(layoutInflater,viewGroup,false)
        _binding = getBinding(inflater, container)
        return _binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        TAG = javaClass.simpleName + "asdws"
        initView()
        initListener()
        initViewModel()
        initRequest()
        initData()
    }

    abstract  fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?): VB
    abstract fun initView()
    abstract fun initListener()
    abstract fun initViewModel()
    abstract fun initRequest()
    abstract fun initData()

    override fun onPause() {
        super.onPause()
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}