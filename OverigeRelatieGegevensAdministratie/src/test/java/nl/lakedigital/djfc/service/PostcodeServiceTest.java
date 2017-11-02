package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.commons.json.JsonAdres;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class PostcodeServiceTest extends EasyMockSupport {

    private PostcodeService postcodeService = new PostcodeService();

    @Test
    public void testExtraHeerAdres() throws Exception {
        String in = "{\"_embedded\":{\"addresses\":[{\"city\":{\"id\":\"2002\",\"label\":\"Zwartemeer\"},\"letter\":null,\"id\":\"0114200000328613\",\"purpose\":\"woonfunctie\",\"postcode\":\"7894AB\",\"municipality\":{\"id\":\"0114\",\"label\":\"Emmen\"},\"nen5825\":{\"street\":\"EEMSLANDWEG\",\"postcode\":\"7894 AB\"},\"street\":\"Eemslandweg\",\"number\":41,\"province\":{\"id\":\"22\",\"label\":\"Drenthe\"},\"addition\":null,\"geo\":{\"center\":{\"wgs84\":{\"crs\":{\"properties\":{\"name\":\"urn:ogc:def:crs:OGC:1.3:CRS84\"},\"type\":\"name\"},\"type\":\"Point\",\"coordinates\":[7.03615027089,52.7225023671]},\"rd\":{\"crs\":{\"properties\":{\"name\":\"urn:ogc:def:crs:EPSG::28992\"},\"type\":\"name\"},\"type\":\"Point\",\"coordinates\":[266400,527396]}}},\"type\":\"Verblijfsobject\",\"_links\":{\"self\":{\"href\":\"https://postcode-api.apiwise.nl/v2/addresses/0114200000328613/\"}}},{\"city\":{\"id\":\"2002\",\"label\":\"Zwartemeer\"},\"letter\":\"A\",\"id\":\"0114200000369166\",\"purpose\":\"woonfunctie\",\"postcode\":\"7894AB\",\"municipality\":{\"id\":\"0114\",\"label\":\"Emmen\"},\"nen5825\":{\"street\":\"EEMSLANDWEG\",\"postcode\":\"7894 AB\"},\"street\":\"Eemslandweg\",\"number\":41,\"province\":{\"id\":\"22\",\"label\":\"Drenthe\"},\"addition\":null,\"geo\":{\"center\":{\"wgs84\":{\"crs\":{\"properties\":{\"name\":\"urn:ogc:def:crs:OGC:1.3:CRS84\"},\"type\":\"name\"},\"type\":\"Point\",\"coordinates\":[7.03622425148,52.722501342]},\"rd\":{\"crs\":{\"properties\":{\"name\":\"urn:ogc:def:crs:EPSG::28992\"},\"type\":\"name\"},\"type\":\"Point\",\"coordinates\":[266405,527396]}}},\"type\":\"Verblijfsobject\",\"_links\":{\"self\":{\"href\":\"https://postcode-api.apiwise.nl/v2/addresses/0114200000369166/\"}}}]},\"_links\":{\"self\":{\"href\":\"https://postcode-api.apiwise.nl/v2/addresses/?postcode=7894AB&number=41\"}}}";
        JsonAdres verwacht = new JsonAdres();
        verwacht.setStraat("Eemslandweg");
        verwacht.setPlaats("ZWARTEMEER");

        assertEquals(verwacht, postcodeService.extraHeerAdres(in));
    }
}