package com.task.noteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.noteapp.viewmodel.NoteViewModel
import com.task.noteapp.adapter.NoteListAdapter
import com.task.noteapp.databinding.FragmentNotesBinding
import com.task.noteapp.event.DeleteNoteEvent
import com.task.noteapp.event.MoveToSecretNotesEvent
import com.task.noteapp.util.ListAdapterType
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class NoteListFragment : BaseFragment() {
  private lateinit var binding: FragmentNotesBinding
  private lateinit var viewModel: NoteViewModel
  private lateinit var adapter: NoteListAdapter
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentNotesBinding.inflate(inflater, container, false)
    initVM()
    return binding.root
  }

  private fun initVM() {
    viewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)
    viewModel.getNotesObserver().observe(viewLifecycleOwner, { t ->
      binding.imgDataNotFound.isVisible = t.isNullOrEmpty()
      binding.recylerView.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      adapter = NoteListAdapter(t, requireContext(), ListAdapterType.MAIN)
      binding.recylerView.adapter = adapter
    })
  }


  @Subscribe(threadMode = ThreadMode.MAIN)
  fun deleteNoteEvent(event: DeleteNoteEvent) {
    viewModel.deleteById(event.selectedID)
  }


  @Subscribe(threadMode = ThreadMode.MAIN)
  fun moveToSecretNotes(event: MoveToSecretNotesEvent) {
    viewModel.updateSecretById(1, event.id)
  }


  override fun onStart() {
    super.onStart()
    EventBus.getDefault().register(this)
  }

  override fun onStop() {
    super.onStop()
    EventBus.getDefault().unregister(this)
  }
}