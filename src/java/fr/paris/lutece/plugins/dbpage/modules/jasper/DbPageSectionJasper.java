/*
 * Copyright (c) 2002-2013, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.dbpage.modules.jasper;

import fr.paris.lutece.plugins.dbpage.business.DbPageDatabase;
import fr.paris.lutece.plugins.dbpage.business.DbPageDatabaseHome;
import fr.paris.lutece.plugins.dbpage.business.DbPageDatabaseSectionHome;
import fr.paris.lutece.plugins.dbpage.business.DbPageLoaderDatabase;
import fr.paris.lutece.plugins.dbpage.business.section.DbPageSection;
import fr.paris.lutece.plugins.jasper.business.JasperReportHome;
import fr.paris.lutece.plugins.jasper.service.JasperFileLinkService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class DbPageSectionJasper extends DbPageSection
{
    /**
     *
     */
    private static final long serialVersionUID = -3698797709397929643L;
    private static final String PARAMETER_DBPAGE_NAME = "dbpage";
    private static final String TEMPLATE_CREATION_JASPER = "admin/plugins/dbpage/create_section_jasper.html";
    private static final String TEMPLATE_MODIFICATION_JASPER = "admin/plugins/dbpage/modify_section_jasper.html";
    private static final String TEMPLATE_DEFAULT_JASPER = "skin/plugins/dbpage/default_jasper.html";
    private static final String PRPORTY_JASPER_PLUGIN="jasper";
    private static final String MARK_LIST_REPORT = "list_report";
    private static final String MARK_JASPER_CONTENT ="jasper_content";
    private static final String MARK_DBPAGE_NAME="dbpage_name";
    public DbPageSectionJasper( String strDescType )
    {
    	   this.setIdTypeSignature(serialVersionUID);
           this.setDescType(strDescType);
    }

    /**
     * Returns the Html Section Table form
     *
     * @return the html code of the html Section Table form
     * @param request
     *            The request
     * @param listValues
     *            the list of id values substitute in the SQL request
     */
    public String getHtmlSection( List listValues, HttpServletRequest request )
    {
        String strParam = getTitle(  );
        Plugin plugin = PluginService.getPlugin("dbpage");
        String strDbPageName = request.getParameter( PARAMETER_DBPAGE_NAME );
        
        if(DbPageDatabaseSectionHome.isSectionInPage(strDbPageName, strParam, plugin))
        		{
        byte[] arrayContent = JasperFileLinkService.exportFile( request, strParam );
        HashMap rootModel = new HashMap(  );
       
        rootModel.put( MARK_JASPER_CONTENT, new String( arrayContent )); 
        rootModel.put( MARK_DBPAGE_NAME, strDbPageName); 
        HtmlTemplate tJasper = AppTemplateService.getTemplate( TEMPLATE_DEFAULT_JASPER, request.getLocale(  ),
                rootModel );

        return tJasper.getHtml(  );       
        		}
       return "";
    }

    public long getIdType(  )
    {
    	  return getIdTypeSignature();
    }

    public String getTypeDescription(  )
    {
    	  return getDescType();
    }

    public String getCreationTemplate(  )
    {
        return TEMPLATE_CREATION_JASPER;
    }

    public String getModificationTemplate(  )
    {
        return TEMPLATE_MODIFICATION_JASPER;
    }
    /**
     * Returns a map with additional markers
     */
    public Map<String, Object> getMarkMap(  )
    {
        Map map = new HashMap(  );
        Plugin plugin = PluginService.getPlugin(PRPORTY_JASPER_PLUGIN);
        Collection collection = JasperReportHome.getJasperReportsList(plugin);
        map.put(MARK_LIST_REPORT, collection);
        return map;
    }
}
