-- -------------------------------------------------------------------------------------------------
-- Database Name: mosip_ida
-- Table Name 	: ida.key_policy_def
-- Purpose    	: Key Policy Defination: Policy related to encryption key management is defined here. For eg. Expiry duration of a key generated.
--           
-- Create By   	: Sadanandegowda DM
-- Created Date	: 21-Apr-2020
-- 
-- Modified Date        Modified By         Comments / Remarks
-- ------------------------------------------------------------------------------------------
-- Jan-2021		Ram Bhatt	    Set is_deleted flag to not null and default false
-- Mar-2021		Ram Bhatt	    Reverting is_deleted not null changes
-- ------------------------------------------------------------------------------------------

-- object: ida.key_policy_def | type: TABLE --
-- DROP TABLE IF EXISTS ida.key_policy_def CASCADE;
CREATE TABLE ida.key_policy_def(
	app_id character varying(36) NOT NULL,
	key_validity_duration smallint,
	is_active boolean NOT NULL,
	cr_by character varying(256) NOT NULL,
	cr_dtimes timestamp NOT NULL,
	upd_by character varying(256),
	upd_dtimes timestamp,
	is_deleted boolean DEFAULT FALSE,
	del_dtimes timestamp,
	CONSTRAINT pk_keypdef_id PRIMARY KEY (app_id)

);
-- ddl-end --
COMMENT ON TABLE ida.key_policy_def IS 'Key Policy Defination: Policy related to encryption key management is defined here. For eg. Expiry duration of a key generated.';
-- ddl-end --
COMMENT ON COLUMN ida.key_policy_def.app_id IS 'Application ID: Application id for which the key policy is defined';
-- ddl-end --
COMMENT ON COLUMN ida.key_policy_def.key_validity_duration IS 'Key Validity Duration: Duration for which key is valid';
-- ddl-end --
COMMENT ON COLUMN ida.key_policy_def.is_active IS 'IS_Active : Flag to mark whether the record is Active or In-active';
-- ddl-end --
COMMENT ON COLUMN ida.key_policy_def.cr_by IS 'Created By : ID or name of the user who create / insert record';
-- ddl-end --
COMMENT ON COLUMN ida.key_policy_def.cr_dtimes IS 'Created DateTimestamp : Date and Timestamp when the record is created/inserted';
-- ddl-end --
COMMENT ON COLUMN ida.key_policy_def.upd_by IS 'Updated By : ID or name of the user who update the record with new values';
-- ddl-end --
COMMENT ON COLUMN ida.key_policy_def.upd_dtimes IS 'Updated DateTimestamp : Date and Timestamp when any of the fields in the record is updated with new values.';
-- ddl-end --
COMMENT ON COLUMN ida.key_policy_def.is_deleted IS 'IS_Deleted : Flag to mark whether the record is Soft deleted.';
-- ddl-end --
COMMENT ON COLUMN ida.key_policy_def.del_dtimes IS 'Deleted DateTimestamp : Date and Timestamp when the record is soft deleted with is_deleted=TRUE';
-- ddl-end --
