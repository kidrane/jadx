package jadx.api;

import jadx.core.codegen.CodeWriter;

import java.io.File;

public class ResourceFile {

	public static final class ZipRef {
		private final File zipFile;
		private final String entryName;

		public ZipRef(File zipFile, String entryName) {
			this.zipFile = zipFile;
			this.entryName = entryName;
		}

		public File getZipFile() {
			return zipFile;
		}

		public String getEntryName() {
			return entryName;
		}

		@Override
		public String toString() {
			return "ZipRef{" + zipFile + ", '" + entryName + "'}";
		}
	}

	private final JadxDecompiler decompiler;
	private final String name;
	private final ResourceType type;
	private ZipRef zipRef;

	ResourceFile(JadxDecompiler decompiler, String name, ResourceType type) {
		this.decompiler = decompiler;
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public ResourceType getType() {
		return type;
	}

	public CodeWriter getContent() {
		return ResourcesLoader.loadContent(decompiler, zipRef, type);
	}

	void setZipRef(ZipRef zipRef) {
		this.zipRef = zipRef;
	}

	@Override
	public String toString() {
		return "ResourceFile{name='" + name + '\'' + ", type=" + type + "}";
	}
}
