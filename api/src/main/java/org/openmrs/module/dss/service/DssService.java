package org.openmrs.module.dss.service;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.logic.result.Result;
import org.openmrs.module.dss.DssRule;
import org.openmrs.module.dss.hibernateBeans.Rule;
import org.springframework.transaction.annotation.Transactional;

/**
 * Defines services used by this module
 *  
 * @author Tammy Dugan
 *
 */
@Transactional
public interface DssService
{
	/**
	 * Runs a list of rules and returns a string result
	 * @param p Patient to run the rules for
	 * @param ruleList List of rules to run
	 * @param defaultPackagePrefix package to look in for pre-compiled rules
	 * @param rulePackagePrefix 
	 * @return String result as a string
	 */
	public String runRulesAsString(Patient p, ArrayList<Rule> ruleList,
			String defaultPackagePrefix,String rulePackagePrefix);

	/**
	 * Runs a single rule and returns the result as an openmrs Result object
	 * @param p Patient to run the rules for
	 * @param rule single rule to evaluate
	 * @param defaultPackagePrefix package to look in for pre-compiled rules
	 * @param rulePackagePrefix 
	 * @return String result as an openmrs Result object
	 */
	public Result runRule(Patient p, Rule rule, 
			String defaultPackagePrefix,String rulePackagePrefix);

	/**
	 * Runs a list of rules and returns an arraylist of openmrs Result objects
	 * @param p Patient to run the rules for
	 * @param ruleList list of rules to evaluate
	 * @param defaultPackagePrefix package to look in for pre-compiled rules
	 * @param rulePackagePrefix 
	 * @return ArrayList of openmrs Result objects
	 */
	public ArrayList<Result> runRules(Patient p, ArrayList<Rule> ruleList,
			String defaultPackagePrefix,
			String rulePackagePrefix);

	/**
	 * Looks up a rule from the dss_rule table by rule_id
	 * @param ruleId unique id for a rule in the dss_rule table
	 * @return Rule rule from the dss_rule table
	 * @throws APIException
	 */
	public Rule getRule(int ruleId) throws APIException;

	/**
	 * Adds a new rule to the dss_rule table
	 * @param classFilename name of the compiled class file that contains the 
	 * executable rule
	 * @param rule DssRule to save to the dss_rule table
	 * @return Rule rule that was added to the dss_rule table
	 * @throws APIException
	 */
	public Rule addRule(String classFilename, DssRule rule) throws APIException;

	/**
	 * Deletes an existing rule from the dss_rule table based on the ruleId
	 * @param ruleId unique id for the rule to be deleted from the dss_rule table
	 */
	public void deleteRule(int ruleId);
		
	public List<Rule> getPrioritizedRules(String type);
	
	public List<Rule> getNonPrioritizedRules(String type);
		
	/**
	 * Returns a list of rules from the dss_rule table that match the criteria
	 * assigned to the rule parameter
	 * @param rule Rule whose assigned attributes indicate the restrictions
	 * of the dss_rule table query
	 * @param ignoreCase String attributes assigned in the Rule parameter should
	 * be matched in the dss_rule query regardless of case
	 * @param enableLike String attributes assigned in the Rule parameter should
	 * be matched in the dss_rule query using LIKE instead of exact matching
	 * @return List<Rule>
	 */
	public List<Rule> getRules(Rule rule, boolean ignoreCase, boolean enableLike, String sortColumn);

	/**
	 * This method tries to load a class based on the rule name and a set of package names
	 * First, it tries to load the rule from the rulePackagePrefix package. By default, this package
	 * is where the rules that are compiled on the fly live. 
	 * Second, it tries to load the rule from the defaultPackagePrefix package. By default, this package 
	 * contains the default library of pre-compiled rules
	 * Finally, it tries to load the rule with no package prefix
	 * @param rule 
	 * @param defaultPackagePrefix
	 * @param rulePackagePrefix 
	 * @throws Exception
	 */
	public void loadRule(String rule, String defaultPackagePrefix,
			String rulePackagePrefix, boolean updateRule)
		throws Exception;

	/**
	 * Loads a rule into the openmrs LogicService in preparation for executing it
	 * @param rule name that the rule will be stored under in the openmrs LogicService
	 * @throws Exception
	 */
	public void loadRule(String rule, boolean updateRule) throws Exception;
}