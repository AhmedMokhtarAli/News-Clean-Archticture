package com.example.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.utilis.api.CustomErrorThrow
import com.example.utilis.api.NetworkState
import com.example.utilis.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


@AndroidEntryPoint
abstract class BaseFragment(private val layoutResource: Int)
    : Fragment() {
    private var fragmentView:ViewGroup? = null
    private val baseViewModel:BaseViewModel by activityViewModels()
    var savedInstanceState:Bundle?=null
    private var baseActivity: BaseActivty? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResource,container,false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        baseActivity?.hideProgress()
        fragmentView = null
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is BaseActivty){
            val activty = context as BaseActivty
            this.baseActivity=activty
        }
    }
    override fun onDetach() {
        super.onDetach()
        baseActivity=null
    }

    protected fun handleErrorGeneral(th: Throwable, func: (() -> Unit)? = null): CustomErrorThrow? {
        return baseActivity?.handleErrorGeneral(th, func)
    }

    fun Fragment.handeSharedFlow(
        flow:SharedFlow<NetworkState>,
        lifeCycle:Lifecycle.State=Lifecycle.State.STARTED,
        onSuccess: (data:Any) -> Unit,
        onError: ((throwable: Throwable) -> Unit)? = null
    ){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifeCycle){
                flow.collect{netWorkState ->
                    when(netWorkState){
                        is NetworkState.Success<*> -> {
                            onSuccess(netWorkState.data!!)
                        }
                        is NetworkState.Erorr ->{
                            if (onError == null)
                                handleErrorGeneral(netWorkState.throwable)
                            else
                                onError(netWorkState.throwable)
                        }
                        is NetworkState.Loading -> {
                            baseActivity?.showProgress()
                        }
                        is NetworkState.DisMissLoading -> {
                            baseActivity?.hideProgress()
                        }
                    }
                }
            }
        }
    }



}
/** usage
class HomeFragment() : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
super.onViewCreated(view, savedInstanceState)

binding.homeText.text = "Hello view binding"
}
}*/
