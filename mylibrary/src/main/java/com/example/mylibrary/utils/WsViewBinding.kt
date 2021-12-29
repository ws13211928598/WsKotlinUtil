package com.example.mylibrary.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 *  created by ws
 *   on 2021/6/18
 *   describe:
 */
inline fun <reified VB:ViewBinding> RecyclerView.ViewHolder.viewBinding(crossinline black:(View)->VB) = black.invoke(this.itemView)
class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
inline fun <reified T : ViewBinding> newBindingViewHolder(parent: ViewGroup): BindingViewHolder<T> {
    val method = T::class.java.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
    val binding = method.invoke(null, LayoutInflater.from(parent.context), parent, false) as T
    return BindingViewHolder(binding)
}
/*
*
class wsRecyclerAdapter(
  private val list: List<String>
) : RecyclerView.Adapter<BindingViewHolder<ItemTextBinding>>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    newBindingViewHolder<ItemTextBinding>(parent)

  override fun onBindViewHolder(holder: BindingViewHolder<ItemTextBinding>, position: Int) {
    val content = list[position]
    holder.binding.tvContent.text = content
  }

  override fun getItemCount() = list.size
}*/

inline fun <reified VB:ViewBinding> AppCompatActivity.viewBinding() = DelegateBinding(VB::class.java)
class DelegateBinding<VB: ViewBinding>(val cls:Class<VB>) {
    private var binding:VB ?= null

    operator fun getValue(activity: AppCompatActivity, property: KProperty<*>):VB{
        if(binding ==null){
            val declaredMethod = cls.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java
            )
            binding= declaredMethod.invoke(null,activity.layoutInflater) as VB
            activity.setContentView(binding!!.root)
        }
        return  binding!!
    }
}

inline fun <reified VB : ViewBinding> Activity.inflate() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}

inline fun <reified VB : ViewBinding> Dialog.inflate() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}
/*
wsViewBinding
* class WsbaseActivity : AppCompatActivity() {

  private val binding: ActivityMainBinding by inflate()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.tvHelloWorld.text = "Hello Android!"
  }
}*/


@Suppress("UNCHECKED_CAST")
inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB

inline fun <reified VB : ViewBinding> Fragment.bindView() =
    FragmentBindingDelegate(VB::class.java)

class FragmentBindingDelegate<VB : ViewBinding>(
    private val clazz: Class<VB>
) : ReadOnlyProperty<Fragment, VB> {

    private var isInitialized = false
    private var _binding: VB? = null
    private val binding: VB get() = _binding!!

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (!isInitialized) {
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroyView() {
                    _binding = null
                }
            })
            _binding = clazz.getMethod("bind", View::class.java)
                .invoke(null, thisRef.requireView()) as VB
            isInitialized = true
        }
        return binding
    }
}
/*
class wsbasefragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by bindView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHelloWorld.text = "Hello Android!"
    }
}*/
