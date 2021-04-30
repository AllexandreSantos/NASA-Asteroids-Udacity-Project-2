package com.allexandresantos.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
<<<<<<< Updated upstream:app/src/main/java/com/allexandresantos/asteroidradar/main/MainFragment.kt
import com.allexandresantos.asteroidradar.databinding.FragmentMainBinding
import com.allexandresantos.asteroidradar.main.adapters.AsteroidListAdapter
||||||| constructed merge base:app/src/main/java/com/udacity/asteroidradar/main/MainFragment.kt
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.main.adapters.AsteroidListAdapter
=======
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.main.adapters.AsteroidListAdapter
>>>>>>> Stashed changes:app/src/main/java/com/udacity/asteroidradar/main/MainFragment.kt

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy{
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
    ViewModelProviders.of(this, MainViewModelFactory(activity.application)).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = AsteroidListAdapter(AsteroidListAdapter.OnClickListener{
            //Passing this function as a parameter
            viewModel.navigateToAsteroidDetails(it)
        })

        viewModel.navigateToAsteroidDetail.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let { asteroid ->
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.main_overflow_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return true
//    }

}
