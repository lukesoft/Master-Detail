package com.lukemadzedze.zapperdisplay.persons.viewmodel;

import com.lukemadzedze.zapperdisplay.persons.data.repo.PersonsRepository;
import com.lukemadzedze.zapperdisplay.persons.data.repo.TeamRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class MainViewModelTest {

    @Mock
    private PersonsRepository mockPersonsRepository;

    @Mock
    private TeamRepository mockTeamRepository;

    private MainViewModel mainViewModel;

    private int mockSelecetedPersonId = 99;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        mainViewModel = new MainViewModel(mockPersonsRepository, mockTeamRepository);
        mainViewModel.setSelectedPersonId(mockSelecetedPersonId);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setSelectedPersonId() {
        mainViewModel.setSelectedPersonId(91);
        assertEquals(91, mainViewModel.getSelectedPersonId());
    }

    @Test
    public void getSelectedPersonId() {
        assertEquals(mockSelecetedPersonId, mainViewModel.getSelectedPersonId());
    }

    @Test
    public void testGivenSelectedPersonShouldCallCorrectRepo() {
        mainViewModel.setSelectedPersonId(11);
        mainViewModel.getTeamByPersonId();
        verify(mockTeamRepository).getTeamByPersonId(11);
    }

    @Test
    public void shouldCallPersonsRepo() {
        mainViewModel.getPersons();

        verify(mockPersonsRepository).getPersons();
    }
}