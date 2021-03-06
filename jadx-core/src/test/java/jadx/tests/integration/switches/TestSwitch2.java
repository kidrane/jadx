package jadx.tests.integration.switches;

import jadx.core.dex.nodes.ClassNode;
import jadx.tests.api.IntegrationTest;

import org.junit.Test;

import static jadx.tests.api.utils.JadxMatchers.countString;
import static org.junit.Assert.assertThat;

public class TestSwitch2 extends IntegrationTest {
	public static class TestCls {
		boolean isLongtouchable;
		boolean isMultiTouchZoom;
		boolean isCanZoomIn;
		boolean isCanZoomOut;
		boolean isScrolling;
		float multiTouchZoomOldDist;

		void test(int action) {
			switch (action & 255) {
				case 0:
					this.isLongtouchable = true;
					break;
				case 1:
				case 6:
					if (this.isMultiTouchZoom) {
						this.isMultiTouchZoom = false;
					}
					break;
				case 2:
					if (this.isMultiTouchZoom) {
						float dist = multiTouchZoomOldDist;
						if (Math.abs(dist - this.multiTouchZoomOldDist) > 10.0f) {
							float scale = dist / this.multiTouchZoomOldDist;
							if ((scale > 1.0f && this.isCanZoomIn) || (scale < 1.0f && this.isCanZoomOut)) {
								this.multiTouchZoomOldDist = dist;
							}
						}
						return;
					}
					break;
				case 5:
					this.multiTouchZoomOldDist = action;
					if (this.multiTouchZoomOldDist > 10.0f) {
						this.isMultiTouchZoom = true;
						this.isLongtouchable = false;
						return;
					}
					break;
			}
			if (this.isScrolling && action == 1) {
				this.isScrolling = false;
			}
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);
		String code = cls.getCode().toString();

		assertThat(code, countString(4, "break;"));

		// TODO: remove redundant returns
		// assertThat(code, countString(2, "return;"));
	}
}
