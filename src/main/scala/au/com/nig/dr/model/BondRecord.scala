package au.com.nig.dr.model

import java.sql.Date

case class BondRecord(startTenancyDate: Date, lodgementDate: Date, bondAmount: String, weeklyAmount: String,
                      dwellingType: String, bedroomsNb: String, postcode: String, suburb: String)
