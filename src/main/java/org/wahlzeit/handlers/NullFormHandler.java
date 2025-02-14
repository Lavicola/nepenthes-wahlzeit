/*
 * SPDX-FileCopyrightText: 2006-2009 Dirk Riehle <dirk@riehle.org> https://dirkriehle.com
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.handlers;

import org.wahlzeit.model.AccessRights;
import org.wahlzeit.model.PatternInstance;
import org.wahlzeit.model.UserSession;
import org.wahlzeit.webparts.WebPart;


/**
 * A handler class for a null web form.
 */
@PatternInstance(
		patternName = "Null Object Pattern",
		participants = { "NullFormHandler(Null Object)",
				        "Concrete Operation/Object(e.g.AdminUserProfileFormHandler or ChangePasswordFormHandler) "
		}
)
public class NullFormHandler extends AbstractWebFormHandler {
	
	/**
	 * 
	 */
	public NullFormHandler() {
		initialize(PartUtil.NULL_FORM_FILE, AccessRights.GUEST);
	}
	
	/**
	 * 
	 */
	protected void doMakeWebPart(UserSession us, WebPart part) {
		// do nothing
	}
	
}
