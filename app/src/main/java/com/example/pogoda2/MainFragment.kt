package com.example.pogoda2

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pogoda2.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    lateinit var weatherAdapter: WeatherAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherAdapter= WeatherAdapter()
        binding.recyclerview.adapter=weatherAdapter
        val retrofit=RetrofitHelper.getInstanse().create(NetworkApi::class.java)
        val factory = MainViewModelFectory(retrofit, requireActivity().application)
        viewModel=ViewModelProvider(requireActivity(),factory)[MainViewModel::class.java]
        binding.btnSearch.setOnClickListener{
            val text=binding.editSearch.text.toString()
            viewModel.getWeather("894984c82a914c5b8ad64517221609",text,"3")

        }
        viewModel.weather.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Error -> {
                    binding.progressbar.visibility=View.GONE
                    Toast.makeText(requireContext(),it.data.toString(), Toast.LENGTH_SHORT).show()

                }
                is NetworkResult.Loading -> {
                    binding.progressbar.visibility=View.VISIBLE

                }
                is NetworkResult.Success -> {
                    val response=it.data
                        Glide.with(requireActivity())
                            .load("https:" + response?.current?.condition?.icon)
                            .into(binding.imgCurret)
                    Toast.makeText(requireContext(),it.data.toString(), Toast.LENGTH_SHORT).show()

                    binding.tempCurrenta.text=response?.current?.tempC.toString()+"°C"
                    weatherAdapter.submitList(response?.forecast?.forecastday)
                    binding.progressbar.visibility=View.GONE



                }
            }
        }
    }

}