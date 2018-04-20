package com.prefabware.tec.commons.spring.string;

import java.util.Map;

/**
 * a string template with placeholders, that can be resolved using expand
 *
 * Created by stefan on 14.10.16.
 */
public class Template {
	/**
	 * the string with the template cxontaining placeholders
	 */
	final String template;
	/**
	 * placeholder names in the template are prefixed with this	 *
	 */
	String prefix = "<";

	/**
	 * placeholder names in the template are sufixed with this
	 */
	String suffix = ">";

	public Template(String template) {
		this.template = template;
	}

	/**
	 * to create a template with custom placeholder prefix and suffix
	 * using ${} is problematic, cause if the template is a application.property
	 * spring will try to resolve those placeholders, which normally is not what we need !
	 * @param template
	 * @param prefix
	 * @param suffix
	 */
	public Template(String template, String prefix, String suffix) {
		this(template);
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public String expand(Map<String,String> values) {
		Collector collected = values.entrySet().stream().
				collect(Collector::new, Collector::accept, Collector::combine);
		return collected.expanded;
	}

	/**
	 *
	 * @param name - if a placeholder with that name cannot be found,
	 *             it is silently ignored
	 * @param value must not be null, you can pass an empty String if needed
	 * @return
	 */
	public String expand(String name, String value) {
		String placeholder = placeholder(name);
		return template.replace(placeholder, value);
	}

	/**
	 * to create a placeholder with the given name
	 * @param name
	 * @return
	 */
	protected String placeholder(String name) {
		return prefix + name + suffix;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Template other = (Template) o;

		return template != null ? template.equals(other.template) : other.template == null;

	}

	@Override
	public int hashCode() {
		return template != null ? template.hashCode() : 0;
	}

	@Override
	public String toString() {
		return template;
	}

	class Collector{
		String expanded;

		Collector() {
			this.expanded=template;
		}

		/**
		 *
		 * @param e
		 */
		void accept(Map.Entry<String,String> e) {
			if(e.getValue()==null){
				//null cannot be used in String.replace, so best choice is to reject
				throw new IllegalArgumentException("values for placeholders must not be null but found "+e.toString());
			}
			expanded=expanded.replace(placeholder(e.getKey()), e.getValue());
		}

		/**
		 * combining two partially replaced expanded strings is not possible.
		 * As we do not expand using parallel(), combine should never be called
		 * @param other
		 */
		void combine(Collector other) {
			throw new IllegalStateException("combining two partially expanded templates is not possible");
		}
	}
}
