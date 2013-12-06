package ch.uzh.ifi.se.yapp.testsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ch.uzh.ifi.se.yapp.backend.election.ElectionAdapterTest;
import ch.uzh.ifi.se.yapp.backend.geo.GeoDataAdapterTest;
import ch.uzh.ifi.se.yapp.backend.landscape.LandscapeAdapterTest;
import ch.uzh.ifi.se.yapp.backend.visualisation.VisualisationAdapterTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({ ElectionAdapterTest.class, GeoDataAdapterTest.class, LandscapeAdapterTest.class, VisualisationAdapterTest.class })
public class BackendTestSuite {
}
