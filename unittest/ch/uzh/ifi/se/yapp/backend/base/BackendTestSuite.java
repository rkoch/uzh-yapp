package ch.uzh.ifi.se.yapp.backend.base;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ch.uzh.ifi.se.yapp.backend.election.ElectionAdapterTest;
import ch.uzh.ifi.se.yapp.backend.geo.GeoDataAdapterTest;
import ch.uzh.ifi.se.yapp.backend.landscape.LandscapeAdapterTest;
import ch.uzh.ifi.se.yapp.backend.visualisation.VisualizationAdapterTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({ ElectionAdapterTest.class, GeoDataAdapterTest.class, LandscapeAdapterTest.class, VisualizationAdapterTest.class })
public class BackendTestSuite {
}
