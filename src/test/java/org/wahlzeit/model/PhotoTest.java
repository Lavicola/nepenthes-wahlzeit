package org.wahlzeit.model;
import org.wahlzeit.services.Language;
import org.wahlzeit.utils.StringUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PhotoTest {

    @Mock
    public ResultSet resultSet;
    public Location location = new Location(1.0, 1.0, 1.0);

    @Before
    public void setUp() {
        try {
            Mockito.when(resultSet.getInt("owner_id")).thenReturn(1);
            Mockito.when(resultSet.getString("owner_name")).thenReturn("admin");
            Mockito.when(resultSet.getBoolean("owner_notify_about_praise")).thenReturn(false);
            Mockito.when(resultSet.getString("owner_email_address")).thenReturn("admin@aol.de");
            Mockito.when(resultSet.getInt("owner_language")).thenReturn(0);
            Mockito.when(resultSet.getString("owner_home_page")).thenReturn("http://wahlzeit.org");
            Mockito.when(resultSet.getInt("width")).thenReturn(1);
            Mockito.when(resultSet.getInt("height")).thenReturn(1);
            Mockito.when(resultSet.getString("tags")).thenReturn("test");
            Mockito.when(resultSet.getInt("status")).thenReturn(1);
            Mockito.when(resultSet.getInt("praise_sum")).thenReturn(1);
            Mockito.when(resultSet.getInt("no_votes")).thenReturn(1);
            Mockito.when(resultSet.getLong("creation_time")).thenReturn((long)55);
            Mockito.when(resultSet.getDouble("coordinate_x")).thenReturn(5.0);
            Mockito.when(resultSet.getDouble("coordinate_y")).thenReturn(5.0);
            Mockito.when(resultSet.getDouble("coordinate_z")).thenReturn(5.0);

                } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        @Test
        public void testReadFrom() throws SQLException {
            final Photo photo = new Photo(resultSet);
            assertEquals(1, photo.getOwnerId());
            assertEquals(1, photo.getOwnerId());
            assertFalse(photo.getOwnerNotifyAboutPraise());
            assertEquals(photo.ownerEmailAddress, photo.getOwnerEmailAddress());
            assertEquals(Language.ENGLISH, photo.getOwnerLanguage());
            assertEquals(StringUtil.asUrl("http://wahlzeit.org"), photo.getOwnerHomePage());
            assertEquals(1, photo.getWidth());
            assertEquals(1, photo.getHeight());
            assertEquals(new Tags("test"), photo.getTags());
            assertEquals(PhotoStatus.getFromInt(1), photo.getStatus());
            assertEquals((long)55, photo.getCreationTime());

        }

        // test creating a photo with location and without location
        @Test
        public void testWriteOn() throws SQLException {
            final Photo photo = new Photo(resultSet,location);
            final Photo photo2 = new Photo(resultSet);

            photo.writeOn(resultSet);
            photo2.writeOn(resultSet);
            // both should be called twice one for with location and one for without location
            verify(resultSet, times(2)).updateInt(eq("owner_id"), anyInt());
            verify(resultSet, times(2)).updateString(eq("owner_name"), anyString());
            verify(resultSet, times(2)).updateBoolean(eq("owner_notify_about_praise"), anyBoolean());
            verify(resultSet, times(2)).updateString(eq("owner_email_address"), anyString());
            verify(resultSet, times(2)).updateInt(eq("owner_language"), anyInt());
            verify(resultSet, times(2)).updateString(eq("owner_home_page"), anyString());
            verify(resultSet, times(2)).updateInt(eq("width"), anyInt());
            verify(resultSet, times(2)).updateInt(eq("height"), anyInt());
            verify(resultSet, times(2)).updateString(eq("tags"), anyString());
            verify(resultSet, times(2)).updateInt(eq("status"), anyInt());
            verify(resultSet, times(2)).updateInt(eq("praise_sum"), anyInt());           
            verify(resultSet, times(2)).updateInt(eq("no_votes"), anyInt());           
            verify(resultSet, times(2)).updateLong(eq("creation_time"), anyLong());
            verify(resultSet, times(1)).updateDouble(eq("coordinate_x"), anyDouble());
            verify(resultSet, times(1)).updateDouble(eq("coordinate_y"), anyDouble());
            verify(resultSet, times(1)).updateDouble(eq("coordinate_z"), anyDouble());   
        }

        
        @Test(expected = NullPointerException.class)
        public void testNullArgument() throws SQLException {
            Photo photo = new Photo();
            photo.writeOn(null);
        }


    }




