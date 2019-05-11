package driver;

class NameAndParams {
    private String mName;
    private String mRawParams;
    private String[] mParams;

    public NameAndParams(String info) {
	int lParenIndex = info.indexOf('(');
	mName = info.substring(0, lParenIndex);
	mRawParams = info.substring(lParenIndex + 1, info.indexOf(')', lParenIndex));
	mParams = mRawParams.split(",");
	for (int i = 0; i < mParams.length; i++) {
	    mParams[i] = mParams[i].trim();
	}
    }

    public String name() {
	return mName;
    }
    public String fullParams() {
	return mRawParams;
    }
    public String paramAt(int i) {
	return mParams[i];
    }
}
