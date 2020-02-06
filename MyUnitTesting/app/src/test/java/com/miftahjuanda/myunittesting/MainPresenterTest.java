package com.miftahjuanda.myunittesting;import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MainPresenterTest {
    @Mock
    private MainPresenter presenter;
    private MainView view;

    @Before
    public void setUp() {
        view = mock(MainView.class);
        presenter = new MainPresenter(view);
    }

    @Test
    public void testVolumeWithIntegerInput (){
        double volume = presenter.volume(2, 8, 1);
        assertEquals(16, volume, 0.0001);
    }
}